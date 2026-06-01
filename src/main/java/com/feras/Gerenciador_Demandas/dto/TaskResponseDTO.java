package com.feras.Gerenciador_Demandas.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TaskResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String status;
    private String emailUsuario;
    private LocalDateTime criadoEm;
    private List<String> tags;

    public TaskResponseDTO() {
    }

    public TaskResponseDTO(Long id, String titulo, String descricao, String emailUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.emailUsuario = emailUsuario;
    }



    @Override
    public String toString() {
        return "id: " + id +
                "\n titulo: " + titulo +
                "\n descricao: " + descricao +
                "\n status: " + status +
                "\n email: " + emailUsuario +
                "\n criado em:" + criadoEm +
                "\n tags: " + tags;
    }
}