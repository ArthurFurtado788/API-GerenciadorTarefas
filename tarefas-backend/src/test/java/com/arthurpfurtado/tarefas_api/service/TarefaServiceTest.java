package com.arthurpfurtado.tarefas_api.service;

import com.arthurpfurtado.tarefas_api.model.Tarefa;
import com.arthurpfurtado.tarefas_api.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    private Tarefa tarefa;

    @BeforeEach
    void setUp() {
        tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setTitulo("Tarefa de Teste");
        tarefa.setDescricao("Descrição de teste");
        tarefa.setConcluida(false);
    }

    @Test
    void deveListarTodasTarefas() {
        // Arrange
        List<Tarefa> tarefas = Arrays.asList(tarefa);
        when(tarefaRepository.findAll()).thenReturn(tarefas);

        // Act
        List<Tarefa> resultado = tarefaService.listarTodas();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Tarefa de Teste", resultado.get(0).getTitulo());
        verify(tarefaRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarTarefaPorId() {
        // Arrange
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));

        // Act
        Tarefa resultado = tarefaService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Tarefa de Teste", resultado.getTitulo());
        verify(tarefaRepository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoEncontrada() {
        // Arrange
        when(tarefaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tarefaService.buscarPorId(1L);
        });
        assertTrue(exception.getMessage().contains("Tarefa não encontrada com ID: 1"));
    }

    @Test
    void deveCriarTarefa() {
        // Arrange
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        // Act
        Tarefa resultado = tarefaService.criar(tarefa);

        // Assert
        assertNotNull(resultado);
        assertEquals("Tarefa de Teste", resultado.getTitulo());
        verify(tarefaRepository, times(1)).save(tarefa);
    }

    @Test
    void deveLancarExcecaoQuandoCriarTarefaSemTitulo() {
        // Arrange
        tarefa.setTitulo("");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tarefaService.criar(tarefa);
        });
        assertEquals("Título da tarefa é obrigatório", exception.getMessage());
    }

    @Test
    void deveAtualizarTarefa() {
        // Arrange
        Tarefa tarefaAtualizada = new Tarefa();
        tarefaAtualizada.setTitulo("Título Atualizado");
        tarefaAtualizada.setDescricao("Descrição atualizada");
        tarefaAtualizada.setConcluida(true);

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        // Act
        Tarefa resultado = tarefaService.atualizar(1L, tarefaAtualizada);

        // Assert
        assertNotNull(resultado);
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).save(tarefa);
    }

    @Test
    void deveDeletarTarefa() {
        // Arrange
        when(tarefaRepository.existsById(1L)).thenReturn(true);

        // Act
        tarefaService.deletar(1L);

        // Assert
        verify(tarefaRepository, times(1)).existsById(1L);
        verify(tarefaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoDeletarTarefaInexistente() {
        // Arrange
        when(tarefaRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tarefaService.deletar(1L);
        });
        assertTrue(exception.getMessage().contains("Tarefa não encontrada com ID: 1"));
    }
}