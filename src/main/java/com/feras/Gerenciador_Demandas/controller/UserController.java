package com.feras.Gerenciador_Demandas.controller;

import com.feras.Gerenciador_Demandas.dto.UserRequestDTO;
import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "Gerenciamento de usuários")
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Listar usuários")
    @GetMapping("/listarUser")
    public ResponseEntity<String> listar(Users user) {
        userService.listarUsers(user);
        return ResponseEntity.ok().body("Lista de usuários: " + userService.listarUsers(user).toString());
    }

    @Operation(summary = "Cadastrar novo usuário")
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody UserRequestDTO dto) {
        userService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
    }

    @Operation(summary = "Realizar login do usuário")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users login) {
        userService.login(login);
        return ResponseEntity.ok("Usuario logado com sucesso!");
    }

    @Operation(summary = "Atualizar usuário por e-mail")
    @PutMapping("/atualizar/{email}")
    public ResponseEntity<String> atualizar(@PathVariable String email, @RequestBody Users atualizacao) {
        userService.atualizar(email, atualizacao);
        return ResponseEntity.ok("Usuario atualizado com sucesso!");
    }

    @Operation(summary = "Deletar usuário por e-mail")
    @DeleteMapping("/deletar/{email}")
    public ResponseEntity<String> deleta(@PathVariable String email) {
        userService.deletar(email);
        return ResponseEntity.ok("Usuario deletado com sucesso!");
    }

    @Operation(summary = "Alterar senha do usuário")
    @PatchMapping("/alterarSenha/{email}")
    public ResponseEntity<String> alterarSenha(@PathVariable String email, @RequestBody Users alteracao) {
        userService.alterarSenha(email, alteracao);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
}