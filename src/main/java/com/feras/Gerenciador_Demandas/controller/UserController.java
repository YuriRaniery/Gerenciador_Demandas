package com.feras.Gerenciador_Demandas.controller;

import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody Users cadastro) {
        userService.cadastrar(cadastro);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users login) {
        userService.login(login);
        return ResponseEntity.ok("Usuario logado com sucesso!");
    }

    @Transactional
    @PutMapping("/atualizar/{email}")
    public ResponseEntity<String> atualizar(@PathVariable String email, @RequestBody Users atualizacao) {
        userService.atualizar(email, atualizacao);
        return ResponseEntity.ok("Usuario atualizado com sucesso!");
    }

    @Transactional
    @DeleteMapping("/deletar/{email}")
    public ResponseEntity<String> deleta(@PathVariable String email) {
        userService.deletar(email);
        return ResponseEntity.ok("Usuario deletado com sucesso!");
    }

    @Transactional
    @PatchMapping("/alterarSenha/{email}")
    public ResponseEntity<String> alterarSenha(@PathVariable String email, @RequestBody Users alteracao) {
        userService.alterarSenha(email, alteracao);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }

}
