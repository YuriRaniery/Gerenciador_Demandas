package com.feras.Gerenciador_Demandas.service;

import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.exception.ResourceNotFoundException;
import com.feras.Gerenciador_Demandas.exception.UserException;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.repository.TaskRepository;
import com.feras.Gerenciador_Demandas.repository.UserRepository;
import com.feras.Gerenciador_Demandas.role.TaskRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private Users usuario;
    private Tasks task;
    private TaskRequestDTO dto;

    @BeforeEach
    void setUp() {
        usuario = new Users();
        usuario.setEmail("jose@gmail.com");
        usuario.setName("José");
        usuario.setPassword("senha123");

        task = new Tasks();
        task.setId(1L);
        task.setTitle("Minha tarefa");
        task.setDescription("Descrição da tarefa");
        task.setStatus(TaskRole.PENDENTE);
        task.setUser(usuario);

        dto = new TaskRequestDTO();
        dto.setTitle("Minha tarefa");
        dto.setDescription("Descrição da tarefa");
    }

    // ─── listarTodos ────────────────────────────────────────────────────────────

    @Test
    void deveListarTodasAsTarefasComPaginacao() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Tasks> page = new PageImpl<>(List.of(task));

        when(taskRepository.findAll(pageable)).thenReturn(page);

        Page<Tasks> resultado = taskService.listarTodos(pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(taskRepository, times(1)).findAll(pageable);
    }

    // ─── criar ──────────────────────────────────────────────────────────────────

    @Test
    void deveCriarTaskComSucesso() {
        when(userRepository.findByEmail("jose@gmail.com")).thenReturn(Optional.of(usuario));
        when(taskRepository.save(any(Tasks.class))).thenReturn(task);

        Tasks resultado = taskService.criar("jose@gmail.com", dto);

        assertNotNull(resultado);
        assertEquals("Minha tarefa", resultado.getTitle());
        assertEquals(usuario, resultado.getUser());
        verify(taskRepository, times(1)).save(any(Tasks.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoCriar() {
        when(userRepository.findByEmail("naocadastrado@gmail.com")).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.criar("naocadastrado@gmail.com", dto)
        );

        assertTrue(ex.getMessage().contains("naocadastrado@gmail.com"));
        verify(taskRepository, never()).save(any());
    }

    // ─── atualizar ──────────────────────────────────────────────────────────────

    @Test
    void deveAtualizarTaskComSucesso() {
        dto.setTitle("Titulo atualizado");
        dto.setDescription("Descrição atualizada");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Tasks.class))).thenReturn(task);

        Tasks resultado = taskService.atualizar(1L, dto);

        assertNotNull(resultado);
        assertEquals("Titulo atualizado", resultado.getTitle());
        verify(taskRepository, times(1)).save(any(Tasks.class));
    }

    @Test
    void deveLancarExcecaoQuandoTaskNaoEncontradaAoAtualizar() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.atualizar(99L, dto)
        );

        verify(taskRepository, never()).save(any());
    }

    @Test
    void deveLancarUserExceptionQuandoIdNuloAoAtualizar() {
        UserException ex = assertThrows(
                UserException.class,
                () -> taskService.atualizar(null, dto)
        );

        assertEquals("O ID da task é obrigatório para atualização", ex.getMessage());
        verify(taskRepository, never()).findById(any());
    }

    // ─── alterRoleTask ──────────────────────────────────────────────────────────

    @Test
    void deveAlterarStatusDaTaskComSucesso() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Tasks.class))).thenReturn(task);

        Tasks resultado = taskService.alterRoleTask(1L, TaskRole.EM_ANDAMENTO);

        assertEquals(TaskRole.EM_ANDAMENTO, resultado.getStatus());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void deveLancarExcecaoQuandoTaskNaoEncontradaAoAlterarStatus() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.alterRoleTask(99L, TaskRole.CONCLUIDA)
        );
    }

    // ─── deletar ────────────────────────────────────────────────────────────────

    @Test
    void deveDeletarTaskComSucesso() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        assertDoesNotThrow(() -> taskService.deletar(1L));

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void deveLancarExcecaoQuandoTaskNaoEncontradaAoDeletar() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.deletar(99L)
        );

        verify(taskRepository, never()).delete(any());
    }
}