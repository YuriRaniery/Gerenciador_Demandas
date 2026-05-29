package com.feras.Gerenciador_Demandas.controller;

import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.dto.TaskResponseDTO;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.repository.TaskRepository;
import com.feras.Gerenciador_Demandas.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/listar")
    public ResponseEntity<Page<TaskResponseDTO>> listarTodos(Pageable pageable){
        Page<Tasks> tasks = taskService.listarTodos(pageable);

        Page<TaskResponseDTO> dtos = tasks.map(t -> new TaskResponseDTO(t.getTitle(), t.getDescription(), t.getUser().getEmail()));
        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping("/criar/{email}")
    public ResponseEntity<String> criar(@PathVariable String email, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.criar(email, taskRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("taskCriada");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.atualizar(id, taskRequestDTO);
        return ResponseEntity.ok("taskAtualizada");
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id){
        taskService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deletado com sucesso!");
    }
}