package com.arthurpfurtado.tarefas_api.repository;

import com.arthurpfurtado.tarefas_api.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository para operações CRUD com a entidade Tarefa
@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
