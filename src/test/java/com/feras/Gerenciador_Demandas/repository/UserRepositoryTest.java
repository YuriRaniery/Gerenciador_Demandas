package com.feras.Gerenciador_Demandas.repository;

import com.feras.Gerenciador_Demandas.model.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserRepository userRepository;

    // ── Teste 1: findByEmail — encontra ──────────────────────────────────
    @Test
    void deveEncontrarUsuarioPorEmail() {
        // ARRANGE
        Users usuario = new Users();
        usuario.setEmail("maria@gmail.com");
        usuario.setName("Maria");
        usuario.setPassword("senha123");
        em.persist(usuario);
        em.flush();

        // ACT
        Optional<Users> resultado = userRepository.findByEmail("maria@gmail.com");

        // ASSERT
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getName()).isEqualTo("Maria");
    }

    // ── Teste 2: findByEmail — não encontra ───────────────────────────────
    @Test
    void deveRetornarVazioQuandoEmailNaoExiste() {
        // ACT
        Optional<Users> resultado = userRepository.findByEmail("naoexiste@gmail.com");

        // ASSERT
        assertThat(resultado).isEmpty();
    }

    // ── Teste 3: save e findAll ───────────────────────────────────────────
    @Test
    void deveSalvarEListarUsuarios() {
        // ARRANGE
        Users u1 = new Users();
        u1.setEmail("ana@gmail.com");
        u1.setName("Ana");
        u1.setPassword("senha123");

        Users u2 = new Users();
        u2.setEmail("pedro@gmail.com");
        u2.setName("Pedro");
        u2.setPassword("senha456");

        em.persist(u1);
        em.persist(u2);
        em.flush();

        // ACT
        var todos = userRepository.findAll();

        // ASSERT
        assertThat(todos).hasSizeGreaterThanOrEqualTo(2);
    }

    // ── Teste 4: delete ───────────────────────────────────────────────────
    @Test
    void deveDeletarUsuarioPorEmail() {
        // ARRANGE
        Users usuario = new Users();
        usuario.setEmail("deletar@gmail.com");
        usuario.setName("Deletar");
        usuario.setPassword("senha123");
        em.persist(usuario);
        em.flush();

        // ACT
        userRepository.delete(usuario);
        Optional<Users> resultado = userRepository.findByEmail("deletar@gmail.com");

        // ASSERT
        assertThat(resultado).isEmpty();
    }
}