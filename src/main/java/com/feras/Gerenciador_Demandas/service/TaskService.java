package com.feras.Gerenciador_Demandas.service;

import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.dto.TaskResponseDTO;
import com.feras.Gerenciador_Demandas.exception.ResourceNotFoundException;
import com.feras.Gerenciador_Demandas.exception.UserException;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.repository.TaskRepository;
import com.feras.Gerenciador_Demandas.repository.UserRepository;
import com.feras.Gerenciador_Demandas.role.TaskRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository tasksRepository;
    private final UserRepository userRepository;

    public Page<Tasks> listarTodos(Pageable pageable) {
        return tasksRepository.findAll(pageable);
    }
    @Transactional
    public Tasks criar(String email, TaskRequestDTO req){
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o email: " + email));
        Tasks task = new Tasks();
        task.setStatus(TaskRole.PENDENTE);
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setUser(user);
        return tasksRepository.save(task);
    }

    @Transactional
    public Tasks atualizar(Long id, TaskRequestDTO req){
        if (id == null) {
            // Mantive a sua UserException aqui pois é um erro de validação (Bad Request 400), e não um "Não Encontrado" (404)
            throw new UserException(HttpStatus.BAD_REQUEST, "O ID da task é obrigatório para atualização");
        }

        Tasks tasksExistente = tasksRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task não encontrada com o ID: " + req.getId()));

        tasksExistente.setTitle(req.getTitle());
        tasksExistente.setDescription(req.getDescription());

        return tasksRepository.save(tasksExistente);
    }

    @Transactional
    public Tasks alterRoleTask(Long id, TaskRole role){
        Tasks task = tasksRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task não encontrada com o ID: " + id));

        task.setStatus(role);
        return tasksRepository.save(task);
    }

    @Transactional
    public void deletar(Long id){
        Tasks task = tasksRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task não encontrada com o ID: " + id));

        tasksRepository.delete(task);
    }
}