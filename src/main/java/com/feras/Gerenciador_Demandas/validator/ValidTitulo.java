package com.feras.Gerenciador_Demandas.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ValidTitulo {
    @Constraint(validatedBy = ValidTituloValidator.class) //aqui ele diz qual classe vai conter a lógica de validação
    @Target({ ElementType.FIELD })//aqui define que essa anotação só pode ser usada em atributos (campos) de uma classe
    @Retention(RetentionPolicy.RUNTIME)//aqui garante que a anotação estará disponível em tempo de execução, permitindo que o Spring/Hibernate leia e execute a validação.
    public @interface validTitulo {
        String message() default "O título contém palavras proibidas.";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }
}
