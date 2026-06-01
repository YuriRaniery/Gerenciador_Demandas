// repository/TaskRepository.java
package com.feras.Gerenciador_Demandas.repository;

import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.role.TaskRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Tasks, Long> {

    // Query method derivado — busca tasks pelo status
    List<Tasks> findByStatus(TaskRole status);

    // Query method derivado — busca tasks pelo email do usuário
    List<Tasks> findByUserEmail(String email);

    // Query JPQL customizada — busca tasks pelo título (contém, ignora maiúsculas)
    @Query("SELECT t FROM Tasks t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<Tasks> buscarPorTitulo(@Param("titulo") String titulo);
}