package com.feras.Gerenciador_Demandas.service;

import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.exception.ResourceNotFoundException;
import com.feras.Gerenciador_Demandas.exception.UserException;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.repository.TaskRepository;
import com.feras.Gerenciador_Demandas.role.TaskRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository tasksRepository;

    public List<Tasks> listar(){
        return tasksRepository.findAll();
    }

    @Transactional
    public Tasks criar(TaskRequestDTO req){
        Tasks task = new Tasks();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        return tasksRepository.save(task);
    }

    @Transactional
    public Tasks atualizar(TaskRequestDTO req){
        if (req.getId() == null) {
            // Mantive a sua UserException aqui pois é um erro de validação (Bad Request 400), e não um "Não Encontrado" (404)
            throw new UserException(HttpStatus.BAD_REQUEST, "O ID da task é obrigatório para atualização");
        }

        Tasks tasksExistente = tasksRepository.findById(req.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task não encontrada com o ID: " + req.getId()));

        tasksExistente.setTitle(req.getTitle());
        tasksExistente.setDescription(req.getDescription());

        return tasksRepository.save(tasksExistente);
    }

    @Transactional
    public Tasks alterRoleTask(Long id, TaskRole role){
        Tasks task = tasksRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task não encontrada com o ID: " + id));

        task.setRole(role);
        return tasksRepository.save(task);
    }

    @Transactional
    public void deletar(TaskRequestDTO req){
        Tasks task = tasksRepository.findById(req.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task não encontrada com o ID: " + req.getId()));

        tasksRepository.delete(task);
    }
}