package com.feras.Gerenciador_Demandas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
public class Users {



    @Id
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String dateOfBirth;

    @OneToMany(mappedBy = "user")
    private List<Tasks> tasks;

}
