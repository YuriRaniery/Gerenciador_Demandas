package com.feras.Gerenciador_Demandas.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidTituloValidator implements ConstraintValidator<ValidTitulo.validTitulo, String> {

    private static final List<String> PROBIDAS=  List.of("spam", "teste123", "porn", "fake", "clickbait");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return PROBIDAS.stream().noneMatch(value.toLowerCase()::contains);
    }

}
