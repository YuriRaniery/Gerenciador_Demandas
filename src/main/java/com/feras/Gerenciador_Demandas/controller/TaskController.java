package com.feras.Gerenciador_Demandas.controller;

import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/criar")
    public Tasks criar(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        Tasks tasks = taskService.criar(taskRequestDTO);
        return new Tasks(tasks.getTitle(), tasks.getDescription());
    }

    @PutMapping("/atualizar")
    public Tasks atualizar(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        Tasks tasks = taskService.atualizar(taskRequestDTO);
        return new Tasks(tasks.getTitle(), tasks.getDescription());
    }
}
