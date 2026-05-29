package com.feras.Gerenciador_Demandas.controller;

import com.feras.Gerenciador_Demandas.dto.UserRequestDTO;
import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/listarUser")
    public ResponseEntity<String> listar(@RequestBody Users user) {
        userService.listarUsers(user);
        return ResponseEntity.ok().body("Lista de usuários: " + userService.listarUsers(user).toString());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody UserRequestDTO dto) {
        userService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users login) {
        userService.login(login);
        return ResponseEntity.ok("Usuario logado com sucesso!");
    }

    @PutMapping("/atualizar/{email}")
    public ResponseEntity<String> atualizar(@PathVariable String email, @RequestBody Users atualizacao) {
        userService.atualizar(email, atualizacao);
        return ResponseEntity.ok("Usuario atualizado com sucesso!");
    }

    @DeleteMapping("/deletar/{email}")
    public ResponseEntity<String> deleta(@PathVariable String email) {
        userService.deletar(email);
        return ResponseEntity.ok("Usuario deletado com sucesso!");
    }

    @PatchMapping("/alterarSenha/{email}")
    public ResponseEntity<String> alterarSenha(@PathVariable String email, @RequestBody Users alteracao) {
        userService.alterarSenha(email, alteracao);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
}