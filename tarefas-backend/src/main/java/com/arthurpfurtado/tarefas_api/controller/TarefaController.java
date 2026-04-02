package com.arthurpfurtado.tarefas_api.controller;

import com.arthurpfurtado.tarefas_api.model.Tarefa;
import com.arthurpfurtado.tarefas_api.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
// Libera o acesso para o seu projeto React (Vite usa a porta 5173 por padrão)
@CrossOrigin(origins = "http://localhost:5173/")
public class TarefaController {

    private final TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas() {
        return ResponseEntity.ok(tarefaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Tarefa> criar(@Valid @RequestBody Tarefa tarefa) {
        // Uma boa prática: retornar status 201 (Created) ao criar recursos
        return ResponseEntity.status(201).body(tarefaService.criar(tarefa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @Valid @RequestBody Tarefa tarefa) {
        return ResponseEntity.ok(tarefaService.atualizar(id, tarefa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}