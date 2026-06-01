package com.feras.Gerenciador_Demandas.controller;

import com.feras.Gerenciador_Demandas.dto.UserRequestDTO;
import com.feras.Gerenciador_Demandas.dto.UserResponseDTO;
import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Gerenciamento de usuários")
@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UserController {

    // 1. Removida a injeção do 'private final Users user'
    private final UserService userService;

    @Operation(summary = "Listar usuários")
    @GetMapping("/listarUsers")
    public ResponseEntity<List<UserResponseDTO>> listar() {
        return ResponseEntity.ok(userService.listarUsers());
    }

    @Operation(summary = "Cadastrar novo usuário")
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody UserRequestDTO dto) {
        userService.cadastrar(dto);
        // 2. Pegamos o nome direto do 'dto' que acabou de chegar
        return ResponseEntity.status(HttpStatus.CREATED).body("O usuário " + dto.getName() + " foi cadastrado com sucesso!");
    }

    @Operation(summary = "Realizar login do usuário")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users login) {
        // 3. Pegamos o usuário retornado pelo banco no service
        Users usuarioLogado = userService.login(login);
        return ResponseEntity.ok("O usuário " + usuarioLogado.getName() + " foi logado com sucesso!");
    }

    @Operation(summary = "Atualizar usuário por e-mail")
    @PutMapping("/atualizar/{email}")
    public ResponseEntity<String> atualizar(@PathVariable String email, @RequestBody Users atualizacao) {
        userService.atualizar(email, atualizacao);
        // 4. Pegamos o nome direto do objeto 'atualizacao'
        return ResponseEntity.ok("O usuário " + atualizacao.getName() + " foi atualizado com sucesso!");
    }

    @Operation(summary = "Deletar usuário por e-mail")
    @DeleteMapping("/deletar/{email}")
    public ResponseEntity<String> deleta(@PathVariable String email) {
        // 5. O service de deletar já retorna o usuário excluído, então usamos ele
        Users usuarioDeletado = userService.deletar(email);
        return ResponseEntity.ok("O usuário " + usuarioDeletado.getName() + " foi deletado com sucesso!");
    }

    @Operation(summary = "Alterar senha do usuário")
    @PatchMapping("/alterarSenha/{email}")
    public ResponseEntity<String> alterarSenha(@PathVariable String email, @RequestBody Users alteracao) {
        userService.alterarSenha(email, alteracao);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
}