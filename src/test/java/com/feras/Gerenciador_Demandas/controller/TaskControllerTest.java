package com.feras.Gerenciador_Demandas.controller;

import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void criarTask_deveRetornar201_quandoDadosValidos() throws Exception {
        String body = """
            {
                "title": "Minha Task",
                "description": "Descricao valida aqui"
            }
            """;

        Tasks taskFake = new Tasks();
        taskFake.setTitle("Minha Task");

        Mockito.when(taskService.criar(eq("a@b.com"), any(TaskRequestDTO.class))).thenReturn(taskFake);

        mockMvc.perform(post("/api/task/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .param("email", "a@b.com"))
                .andExpect(status().isCreated());
    }

    @Test
    void criarTask_deveRetornar400_quandoTituloVazio() throws Exception {
        String body = """
            {
                "title": "",
                "description": "Descricao sem titulo"
            }
            """;

        mockMvc.perform(post("/api/task/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .param("email", "a@b.com"))
                .andExpect(status().isBadRequest());
    }
}