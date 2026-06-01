package com.feras.Gerenciador_Demandas.controller;

import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.dto.TaskResponseDTO;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.role.TaskRole;
import com.feras.Gerenciador_Demandas.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;

    private TaskResponseDTO toDTO(Tasks t) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(t.getId());
        dto.setTitulo(t.getTitle());
        dto.setDescricao(t.getDescription());
        dto.setStatus(t.getStatus().name());
        dto.setEmailUsuario(t.getUser() != null ? t.getUser().getEmail() : null);
        dto.setCriadoEm(t.getDate());
        dto.setTags(t.getTags() != null
                ? t.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList())
                : List.of());
        return dto;
    }

    @Operation(summary = "Listar todas as tasks com paginação")
    @GetMapping("/listar")
    public ResponseEntity<Page<TaskResponseDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(taskService.listarTodos(pageable).map(this::toDTO));
    }

    @Operation(summary = "Listar tasks por email do usuário")
    @GetMapping("/listarId/{email}")
    public ResponseEntity<Page<TaskResponseDTO>> listarId(@PathVariable String email, Pageable pageable) {
        return ResponseEntity.ok(taskService.listarId(email, pageable).map(this::toDTO));
    }

    @Operation(summary = "Criar nova task para um usuário específico")
    @PostMapping("/criar/{email}")
    public ResponseEntity<String> criar(@PathVariable @Email(message = "inválido") String email,
                                        @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        Tasks taskCriada = taskService.criar(email, taskRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("A task de id: " + taskCriada.getId() + " foi criada com sucesso!");
    }

    @Operation(summary = "Atualizar uma task existente por ID")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id,
                                            @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.atualizar(id, taskRequestDTO);
        return ResponseEntity.ok("A task de id: " + id + " foi atualizada com sucesso!");
    }

    @Operation(summary = "Altera o status da Task por Id")
    @PatchMapping("/alterarStatus/{id}")
    public ResponseEntity<String> alterarRole(@PathVariable Long id, @RequestBody TaskRole role) {
        taskService.alterRoleTask(id, role);
        return ResponseEntity.ok("A task de id: " + id + " teve seu status alterado para: " + role);
    }

    @Operation(summary = "Deleta a task por Id")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        taskService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("A task de id: " + id + " foi deletada com sucesso!");
    }
}