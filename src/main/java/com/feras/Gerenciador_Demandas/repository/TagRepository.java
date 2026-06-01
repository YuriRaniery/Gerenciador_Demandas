package com.feras.Gerenciador_Demandas.repository;

import com.feras.Gerenciador_Demandas.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}