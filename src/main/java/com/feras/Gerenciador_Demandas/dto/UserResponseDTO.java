package com.feras.Gerenciador_Demandas.dto;

import com.feras.Gerenciador_Demandas.model.Users;

public record UserResponseDTO(
        String email,
        String nome
) {
    // Método factory: converte Users → DTO
    public static UserResponseDTO from(Users user) {
        return new UserResponseDTO(
                user.getEmail(),
                user.getName()
        );
    }
}