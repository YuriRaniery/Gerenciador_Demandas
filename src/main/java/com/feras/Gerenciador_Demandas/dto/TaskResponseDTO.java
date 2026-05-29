package com.feras.Gerenciador_Demandas.dto;

import com.feras.Gerenciador_Demandas.model.Tasks;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String status;
    private String emailUsuario;
    private LocalDateTime criadoEm;

    public TaskResponseDTO() {
    }

    public TaskResponseDTO(Long id, String titulo, String descricao, String emailUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.emailUsuario = emailUsuario;
    }

    public TaskResponseDTO(String title, String description, String email) {
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\n titulo: " + titulo +
                "\n descricao: " + descricao +
                "\n status: " + status +
                "\n email: " + emailUsuario +
                "\n criado em:" + criadoEm;
    }
}
