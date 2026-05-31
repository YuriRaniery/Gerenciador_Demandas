package com.feras.Gerenciador_Demandas.service;

import com.feras.Gerenciador_Demandas.dto.UserRequestDTO;
import com.feras.Gerenciador_Demandas.exception.UserException;
import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private Users usuario;

    @BeforeEach
    void setUp() {
        usuario = new Users();
        usuario.setEmail("jose@gmail.com");
        usuario.setName("José");
        usuario.setPassword("senhaCriptografada");
    }

    // ─── cadastrar ──────────────────────────────────────────────────────────────

    @Test
    void deveCadastrarUsuarioComSucesso() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setEmail("jose@gmail.com");
        dto.setNome("José");
        dto.setSenha("senha123");

        when(passwordEncoder.encode(anyString())).thenReturn("senhaCriptografada");
        when(userRepository.save(any(Users.class))).thenReturn(usuario);

        Users resultado = userService.cadastrar(dto);

        assertNotNull(resultado);
        assertEquals("jose@gmail.com", resultado.getEmail());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(Users.class));
    }

    // ─── login ──────────────────────────────────────────────────────────────────

    @Test
    void deveFazerLoginComSucesso() {
        Users loginRequest = new Users();
        loginRequest.setEmail("jose@gmail.com");
        loginRequest.setPassword("senha123");

        when(userRepository.findByEmail("jose@gmail.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha123", "senhaCriptografada")).thenReturn(true);

        Users resultado = userService.login(loginRequest);

        assertNotNull(resultado);
        assertEquals("jose@gmail.com", resultado.getEmail());
    }

    @Test
    void deveLancarExcecaoQuandoEmailNaoEncontradoNoLogin() {
        Users loginRequest = new Users();
        loginRequest.setEmail("naoexiste@gmail.com");
        loginRequest.setPassword("senha123");

        when(userRepository.findByEmail("naoexiste@gmail.com")).thenReturn(Optional.empty());

        UserException ex = assertThrows(
                UserException.class,
                () -> userService.login(loginRequest)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaIncorretaNoLogin() {
        Users loginRequest = new Users();
        loginRequest.setEmail("jose@gmail.com");
        loginRequest.setPassword("senhaerrada");

        when(userRepository.findByEmail("jose@gmail.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaerrada", "senhaCriptografada")).thenReturn(false);

        UserException ex = assertThrows(
                UserException.class,
                () -> userService.login(loginRequest)
        );

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());
    }

    // ─── atualizar ──────────────────────────────────────────────────────────────

    @Test
    void deveAtualizarUsuarioComSucesso() {
        Users atualizacao = new Users();
        atualizacao.setName("José Atualizado");
        atualizacao.setEmail("jose@gmail.com");

        when(userRepository.findByEmail("jose@gmail.com")).thenReturn(Optional.of(usuario));
        when(userRepository.save(any(Users.class))).thenReturn(usuario);

        Users resultado = userService.atualizar("jose@gmail.com", atualizacao);

        assertNotNull(resultado);
        verify(userRepository, times(1)).save(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoAtualizar() {
        when(userRepository.findByEmail("naoexiste@gmail.com")).thenReturn(Optional.empty());

        UserException ex = assertThrows(
                UserException.class,
                () -> userService.atualizar("naoexiste@gmail.com", usuario)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    // ─── deletar ────────────────────────────────────────────────────────────────

    @Test
    void deveDeletarUsuarioComSucesso() {
        when(userRepository.findByEmail("jose@gmail.com")).thenReturn(Optional.of(usuario));
        doNothing().when(userRepository).delete(usuario);

        Users resultado = userService.deletar("jose@gmail.com");

        assertNotNull(resultado);
        verify(userRepository, times(1)).delete(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoDeletar() {
        when(userRepository.findByEmail("naoexiste@gmail.com")).thenReturn(Optional.empty());

        UserException ex = assertThrows(
                UserException.class,
                () -> userService.deletar("naoexiste@gmail.com")
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    // ─── alterarSenha ────────────────────────────────────────────────────────────

    @Test
    void deveAlterarSenhaComSucesso() {
        Users novasSenha = new Users();
        novasSenha.setPassword("novaSenha123");

        when(userRepository.findByEmail("jose@gmail.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("novaSenha123")).thenReturn("novaSenhaCriptografada");
        when(userRepository.save(any(Users.class))).thenReturn(usuario);

        Users resultado = userService.alterarSenha("jose@gmail.com", novasSenha);

        assertNotNull(resultado);
        verify(passwordEncoder, times(1)).encode("novaSenha123");
        verify(userRepository, times(1)).save(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoAlterarSenha() {
        when(userRepository.findByEmail("naoexiste@gmail.com")).thenReturn(Optional.empty());

        UserException ex = assertThrows(
                UserException.class,
                () -> userService.alterarSenha("naoexiste@gmail.com", usuario)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    // ─── loadUserByUsername ──────────────────────────────────────────────────────

    @Test
    void deveCarregarUsuarioPorEmailComSucesso() {
        when(userRepository.findByEmail("jose@gmail.com")).thenReturn(Optional.of(usuario));

        var resultado = userService.loadUserByUsername("jose@gmail.com");

        assertNotNull(resultado);
        assertEquals("jose@gmail.com", resultado.getUsername());
    }

    @Test
    void deveLancarExcecaoQuandoEmailNaoEncontradoNoLoadUserByUsername() {
        when(userRepository.findByEmail("naoexiste@gmail.com")).thenReturn(Optional.empty());

        assertThrows(
                UserException.class,
                () -> userService.loadUserByUsername("naoexiste@gmail.com")
        );
    }
}