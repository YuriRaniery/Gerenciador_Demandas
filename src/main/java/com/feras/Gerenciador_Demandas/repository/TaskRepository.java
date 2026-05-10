package com.feras.Gerenciador_Demandas.repository;

import com.feras.Gerenciador_Demandas.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Users> findByTitle(String title);

}
