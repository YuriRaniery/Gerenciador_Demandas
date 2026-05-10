package com.feras.Gerenciador_Demandas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.management.relation.Role;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "demandas")
public class Tasks {
    @Column(nullable = false)
    private String title;
    private String description;

    @CreationTimestamp
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Role role;
}
