package com.feras.Gerenciador_Demandas.controller;

import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody Users usuario) {
        userService.cadastrar(usuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }
}
