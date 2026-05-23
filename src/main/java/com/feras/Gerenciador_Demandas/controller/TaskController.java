package com.feras.Gerenciador_Demandas.controller;

import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/criar")
    public ResponseEntity<Tasks> criar(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        Tasks taskCriada = taskService.criar(taskRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCriada);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Tasks> atualizar(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        Tasks taskAtualizada = taskService.atualizar(taskRequestDTO);
        return ResponseEntity.ok(taskAtualizada);
    }
}