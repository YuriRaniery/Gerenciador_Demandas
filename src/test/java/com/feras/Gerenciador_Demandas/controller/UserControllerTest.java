package com.feras.Gerenciador_Demandas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feras.Gerenciador_Demandas.dto.UserRequestDTO;
import com.feras.Gerenciador_Demandas.exception.UserException;
import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Users usuario;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        usuario = new Users();
        usuario.setEmail("jose@gmail.com");
        usuario.setName("José");
        usuario.setPassword("senha123");
    }

    // ─── POST /usuarios/cadastrar ────────────────────────────────────────────────

    @Test
    void deveCadastrarUsuarioComSucesso() throws Exception {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("José");
        dto.setEmail("jose@gmail.com");
        dto.setPassword("senha123");

        Mockito.when(userService.cadastrar(any(UserRequestDTO.class))).thenReturn(usuario);

        mockMvc.perform(post(URI.create("/usuarios/cadastrar"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Usuário cadastrado com sucesso!"));
    }

    // ─── POST /usuarios/login ────────────────────────────────────────────────────

    @Test
    void deveFazerLoginComSucesso() throws Exception {
        Mockito.when(userService.login(any(Users.class))).thenReturn(usuario);

        mockMvc.perform(post(URI.create("/usuarios/login"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario logado com sucesso!"));
    }

    @Test
    void deveRetornar404QuandoEmailNaoEncontradoNoLogin() throws Exception {
        Users loginInvalido = new Users();
        loginInvalido.setEmail("naoexiste@gmail.com");
        loginInvalido.setPassword("senha123");

        Mockito.doThrow(new UserException(HttpStatus.NOT_FOUND, "E-mail ou senha inválidos"))
                .when(userService).login(any(Users.class));

        mockMvc.perform(post(URI.create("/usuarios/login"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginInvalido)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar401QuandoSenhaIncorretaNoLogin() throws Exception {
        Users loginSenhaErrada = new Users();
        loginSenhaErrada.setEmail("jose@gmail.com");
        loginSenhaErrada.setPassword("senhaerrada");

        Mockito.doThrow(new UserException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos"))
                .when(userService).login(any(Users.class));

        mockMvc.perform(post(URI.create("/usuarios/login"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginSenhaErrada)))
                .andExpect(status().isUnauthorized());
    }

    // ─── PUT /usuarios/atualizar/{email} ─────────────────────────────────────────

    @Test
    void deveAtualizarUsuarioComSucesso() throws Exception {
        Users atualizacao = new Users();
        atualizacao.setName("José Atualizado");
        atualizacao.setEmail("jose@gmail.com");
        atualizacao.setPassword("senha123");

        Mockito.when(userService.atualizar(eq("jose@gmail.com"), any(Users.class)))
                .thenReturn(usuario);

        mockMvc.perform(put(URI.create("/usuarios/atualizar/jose%40gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizacao)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario atualizado com sucesso!"));
    }

    @Test
    void deveRetornar404AoAtualizarUsuarioInexistente() throws Exception {
        Mockito.doThrow(new UserException(HttpStatus.NOT_FOUND, "Usuário não encontrado"))
                .when(userService).atualizar(eq("naoexiste@gmail.com"), any(Users.class));

        mockMvc.perform(put(URI.create("/usuarios/atualizar/naoexiste%40gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isNotFound());
    }

    // ─── DELETE /usuarios/deletar/{email} ────────────────────────────────────────

    @Test
    void deveDeletarUsuarioComSucesso() throws Exception {
        Mockito.when(userService.deletar("jose@gmail.com")).thenReturn(usuario);

        mockMvc.perform(delete(URI.create("/usuarios/deletar/jose%40gmail.com")))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario deletado com sucesso!"));
    }

    @Test
    void deveRetornar404AoDeletarUsuarioInexistente() throws Exception {
        Mockito.doThrow(new UserException(HttpStatus.NOT_FOUND, "Usuário não encontrado"))
                .when(userService).deletar("naoexiste@gmail.com");

        mockMvc.perform(delete(URI.create("/usuarios/deletar/naoexiste%40gmail.com")))
                .andExpect(status().isNotFound());
    }

    // ─── PATCH /usuarios/alterarSenha/{email} ────────────────────────────────────

    @Test
    void deveAlterarSenhaComSucesso() throws Exception {
        Users novaSenha = new Users();
        novaSenha.setPassword("novaSenha123");

        Mockito.when(userService.alterarSenha(eq("jose@gmail.com"), any(Users.class)))
                .thenReturn(usuario);

        mockMvc.perform(patch(URI.create("/usuarios/alterarSenha/jose%40gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaSenha)))
                .andExpect(status().isOk())
                .andExpect(content().string("Senha alterada com sucesso!"));
    }

    @Test
    void deveRetornar404AoAlterarSenhaDeUsuarioInexistente() throws Exception {
        Users novaSenha = new Users();
        novaSenha.setPassword("novaSenha123");

        Mockito.doThrow(new UserException(HttpStatus.NOT_FOUND, "Usuário não encontrado"))
                .when(userService).alterarSenha(eq("naoexiste@gmail.com"), any(Users.class));

        mockMvc.perform(patch(URI.create("/usuarios/alterarSenha/naoexiste%40gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaSenha)))
                .andExpect(status().isNotFound());
    }
}