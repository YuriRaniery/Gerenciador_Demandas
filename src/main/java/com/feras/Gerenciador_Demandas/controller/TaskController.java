package com.feras.Gerenciador_Demandas.controller;

import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.repository.TaskRepository;
import com.feras.Gerenciador_Demandas.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @GetMapping("/listar")
    public ResponseEntity<String> listar() {
        taskRepository.findAll();
        return ResponseEntity.ok().body(taskRepository.findAll().toString());
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criar(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.criar(taskRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("taskCriada");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.atualizar(id, taskRequestDTO);
        return ResponseEntity.ok("taskAtualizada");
    }

    @DeleteMapping("/deletar{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.deletar(taskRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deletado com sucesso!");
    }
}