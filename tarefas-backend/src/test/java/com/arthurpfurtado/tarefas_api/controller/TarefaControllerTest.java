package com.arthurpfurtado.tarefas_api.controller;

import com.arthurpfurtado.tarefas_api.model.Tarefa;
import com.arthurpfurtado.tarefas_api.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaControllerTest {

    @Mock
    private TarefaService tarefaService;

    @InjectMocks
    private TarefaController tarefaController;

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
        when(tarefaService.listarTodas()).thenReturn(tarefas);

        // Act
        ResponseEntity<List<Tarefa>> response = tarefaController.listarTodas();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Tarefa de Teste", response.getBody().get(0).getTitulo());
        verify(tarefaService, times(1)).listarTodas();
    }

    @Test
    void deveBuscarTarefaPorId() {
        // Arrange
        when(tarefaService.buscarPorId(1L)).thenReturn(tarefa);

        // Act
        ResponseEntity<Tarefa> response = tarefaController.buscarPorId(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tarefa de Teste", response.getBody().getTitulo());
        verify(tarefaService, times(1)).buscarPorId(1L);
    }

    @Test
    void deveCriarTarefa() {
        // Arrange
        when(tarefaService.criar(any(Tarefa.class))).thenReturn(tarefa);

        // Act
        ResponseEntity<Tarefa> response = tarefaController.criar(tarefa);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Tarefa de Teste", response.getBody().getTitulo());
        verify(tarefaService, times(1)).criar(any(Tarefa.class));
    }

    @Test
    void deveAtualizarTarefa() {
        // Arrange
        when(tarefaService.atualizar(eq(1L), any(Tarefa.class))).thenReturn(tarefa);

        // Act
        ResponseEntity<Tarefa> response = tarefaController.atualizar(1L, tarefa);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tarefa de Teste", response.getBody().getTitulo());
        verify(tarefaService, times(1)).atualizar(eq(1L), any(Tarefa.class));
    }

    @Test
    void deveDeletarTarefa() {
        // Arrange
        doNothing().when(tarefaService).deletar(1L);

        // Act
        ResponseEntity<Void> response = tarefaController.deletar(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tarefaService, times(1)).deletar(1L);
    }
}