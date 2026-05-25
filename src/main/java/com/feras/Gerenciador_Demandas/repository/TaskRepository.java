package com.feras.Gerenciador_Demandas.repository;

import com.feras.Gerenciador_Demandas.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> findAll();


}
