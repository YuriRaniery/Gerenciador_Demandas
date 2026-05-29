package com.feras.Gerenciador_Demandas.dto;

import com.feras.Gerenciador_Demandas.validator.ValidTitulo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {


    private Long id;

    @ValidTitulo.validTitulo
    @NotBlank(message = "O titulo é obrigatório")
    @Size(min = 5, max = 100, message = "O titulo deve conter entre 5 e 100 caracteres")
    private String title;

    @Size(min = 10, max = 500, message = "A descrição deve conter entre 10 e 500 caracteres")
    private String description;

    @NotBlank(message = "Email do usuário é obrigatório")
    @Email(message = "Email inválido")
    private String emailUsuario;
}
