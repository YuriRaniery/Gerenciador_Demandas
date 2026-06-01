package com.feras.Gerenciador_Demandas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.exception.ResourceNotFoundException;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.role.TaskRole;
import com.feras.Gerenciador_Demandas.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class TaskControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TaskService taskService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Tasks task;
    private TaskRequestDTO dto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        Users usuario = new Users();
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

    // ─── GET /api/task/listar ────────────────────────────────────────────────────

    @Test
    void deveListarTarefasComSucesso() throws Exception {
        Page<Tasks> page = new PageImpl<>(List.of(task), PageRequest.of(0, 10), 1);
        Mockito.when(taskService.listarTodos(any())).thenReturn(page);

        mockMvc.perform(get(URI.create("/api/task/listar")))
                .andExpect(status().isOk());
    }

    // ─── POST /api/task/criar/{email} ────────────────────────────────────────────

    @Test
    void deveCriarTaskComSucesso() throws Exception {
        Mockito.when(taskService.criar(eq("jose@gmail.com"), any())).thenReturn(task);

        mockMvc.perform(post(URI.create("/api/task/criar/jose%40gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveRetornar400QuandoTituloVazio() throws Exception {
        dto.setTitle("");

        mockMvc.perform(post(URI.create("/api/task/criar/jose%40gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400QuandoTituloMenorQue5Caracteres() throws Exception {
        dto.setTitle("abc");

        mockMvc.perform(post(URI.create("/api/task/criar/jose%40gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400QuandoTituloContemPalavraProibida() throws Exception {
        dto.setTitle("isso e spam aqui");

        mockMvc.perform(post(URI.create("/api/task/criar/jose%40gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400QuandoEmailDoBodyInvalido() throws Exception {

        mockMvc.perform(post(URI.create("/api/task/criar/emailinvalido"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar404QuandoUsuarioNaoExiste() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("Usuário não encontrado"))
                .when(taskService).criar(eq("naoexiste@gmail.com"), any());

        mockMvc.perform(post(URI.create("/api/task/criar/naoexiste%40gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    // ─── PUT /api/task/atualizar/{id} ─────────────────────────────────────────────

    @Test
    void deveAtualizarTaskComSucesso() throws Exception {
        Mockito.when(taskService.atualizar(eq(1L), any())).thenReturn(task);

        mockMvc.perform(put(URI.create("/api/task/atualizar/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornar404AoAtualizarTaskInexistente() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("Task não encontrada"))
                .when(taskService).atualizar(eq(99L), any());

        mockMvc.perform(put(URI.create("/api/task/atualizar/99"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    // ─── DELETE /api/task/deletar/{id} ───────────────────────────────────────────

    @Test
    void deveDeletarTaskComSucesso() throws Exception {
        Mockito.doNothing().when(taskService).deletar(1L);

        mockMvc.perform(delete(URI.create("/api/task/deletar/1")))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornar404AoDeletarTaskInexistente() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("Task não encontrada"))
                .when(taskService).deletar(99L);

        mockMvc.perform(delete(URI.create("/api/task/deletar/99")))
                .andExpect(status().isNotFound());
    }
}