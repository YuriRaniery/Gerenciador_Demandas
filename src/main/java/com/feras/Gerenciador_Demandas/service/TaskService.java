package com.feras.Gerenciador_Demandas.service;

import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.repository.TaskRepository;
import com.feras.Gerenciador_Demandas.role.TaskRole;

import com.feras.Gerenciador_Demandas.exception.UserException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository tasksRepository;

    public TaskService(TaskRepository taskRepository) {
        this.tasksRepository = taskRepository;
    }

    public List<Tasks> listar(){
        return tasksRepository.findAll();
    }

    public Tasks criar(TaskRequestDTO req){
        Tasks task = new Tasks();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        return tasksRepository.save(task);
    }

    public Tasks atualizar(TaskRequestDTO req){
        if (req.getId() == null) {
            throw new UserException(HttpStatus.BAD_REQUEST, "O ID da task é obrigatório para atualização");         
        }

        Tasks tasksExistente = tasksRepository.findById(req.getId())
                .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "Task não encontrado"));
        
        tasksExistente.setTitle(req.getTitle());
        tasksExistente.setDescription(req.getDescription());
        
        return tasksRepository.save(tasksExistente);
    }

    public Tasks alterRoleTask(Long id, TaskRole role){
        Tasks task = tasksRepository.findById(id).orElseThrow();
        task.setRole(role);
        return tasksRepository.save(task);
    }

    public void deletar(TaskRequestDTO req){
        Tasks task = tasksRepository.findById(req.getId())
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));

        tasksRepository.delete(task);        

    }
}
