package com.feras.Gerenciador_Demandas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Users {

    private String name;

    @Column(unique = true, nullable = false)
    private String number;

    @Column(nullable = false)
    private String dateOfBirth;

}
