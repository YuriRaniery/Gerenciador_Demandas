package com.feras.Gerenciador_Demandas.dto;

import com.feras.Gerenciador_Demandas.model.Users;

import java.time.LocalDateTime;

public record UserResponseDTO(
        String email,
        String nome,
        LocalDateTime createdAt
) {
    // Método factory: converte Users → DTO
    public static UserResponseDTO from(Users user) {
        return new UserResponseDTO(
                user.getEmail(),
                user.getName(),
                user.getCreatedAt()
        );
    }
}