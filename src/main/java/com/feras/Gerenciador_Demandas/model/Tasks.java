package com.feras.Gerenciador_Demandas.model;

import com.feras.Gerenciador_Demandas.role.TaskRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "demandas")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @CreationTimestamp
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private TaskRole role;

    @ManyToOne
    @JoinColumn(name = "usuario_email")
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public Tasks(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public Tasks() {}


}
