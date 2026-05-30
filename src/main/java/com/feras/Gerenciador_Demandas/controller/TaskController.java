package com.feras.Gerenciador_Demandas.controller;

import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.dto.TaskResponseDTO;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Tasks", description = "Gerenciamento de tarefas")
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Listar todas as tasks paginadas")
    @GetMapping("/listar")
    public ResponseEntity<Page<TaskResponseDTO>> listarTodos(Pageable pageable){
        Page<Tasks> tasks = taskService.listarTodos(pageable);
        Page<TaskResponseDTO> dtos = tasks.map(t -> new TaskResponseDTO(t.getTitle(), t.getDescription(), t.getUser().getEmail()));
        return ResponseEntity.ok().body(dtos);
    }

    @Operation(summary = "Criar nova task")
    @PostMapping("/criar/{email}")
    public ResponseEntity<String> criar(@PathVariable String email, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.criar(email, taskRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("taskCriada");
    }

    @Operation(summary = "Atualizar task por ID")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.atualizar(id, taskRequestDTO);
        return ResponseEntity.ok("taskAtualizada");
    }

    @Operation(summary = "Deletar task por ID")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id){
        taskService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deletado com sucesso!");
    }
}