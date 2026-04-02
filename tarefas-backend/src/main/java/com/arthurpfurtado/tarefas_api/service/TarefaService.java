package com.arthurpfurtado.tarefas_api.service;

import com.arthurpfurtado.tarefas_api.model.Tarefa;
import com.arthurpfurtado.tarefas_api.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// @Service indica pro Spring que essa classe é um serviço
// o Spring passa a gerenciá-la automaticamente
@Service

// @RequiredArgsConstructor é do Lombok
// gera o construtor que injeta o TarefaRepository automaticamente
@RequiredArgsConstructor
public class TarefaService {

    // o Service usa o Repository para falar com o banco
    // ele nunca acessa o banco diretamente
    private final TarefaRepository tarefaRepository;

    // retorna uma lista com todas as tarefas do banco
    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    // busca uma tarefa pelo id
    // se não encontrar, lança um erro em vez de retornar null
    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + id));
    }

    // recebe uma tarefa e salva no banco
    public Tarefa criar(Tarefa tarefa) {
        if (tarefa.getTitulo() == null || tarefa.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Título da tarefa é obrigatório");
        }
        return tarefaRepository.save(tarefa);
    }

    // busca a tarefa existente pelo id
    // atualiza os campos um por um e salva novamente
    public Tarefa atualizar(Long id, Tarefa tarefaAtualizada) {
        Tarefa tarefa = buscarPorId(id);
        if (tarefaAtualizada.getTitulo() != null && !tarefaAtualizada.getTitulo().trim().isEmpty()) {
            tarefa.setTitulo(tarefaAtualizada.getTitulo());
        }
        if (tarefaAtualizada.getDescricao() != null) {
            tarefa.setDescricao(tarefaAtualizada.getDescricao());
        }
        if (tarefaAtualizada.getConcluida() != null) {
            tarefa.setConcluida(tarefaAtualizada.getConcluida());
        }
        return tarefaRepository.save(tarefa);
    }

    // deleta uma tarefa pelo id
    public void deletar(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada com ID: " + id);
        }
        tarefaRepository.deleteById(id);
    }
}