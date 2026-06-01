// test/.../repository/TaskRepositoryTest.java
package com.feras.Gerenciador_Demandas.repository;

import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.role.TaskRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest  // sobe só JPA + H2, sem web, sem services
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager em;  // insere dados direto no H2 para o teste

    @Autowired
    private TaskRepository taskRepository;

    private Users usuario;

    @BeforeEach
    void setUp() {
        // ARRANGE — cria e persiste um usuário antes de cada teste
        usuario = new Users();
        usuario.setEmail("jose@gmail.com");
        usuario.setName("José");
        usuario.setPassword("senha123");
        em.persist(usuario);
        em.flush();
    }

    // ── Teste 1: findByStatus ────────────────────────────────────────────
    @Test
    void deveRetornarTasksPorStatus() {
        // ARRANGE — cria duas tasks com status diferentes
        Tasks taskPendente = new Tasks();
        taskPendente.setTitle("Task Pendente");
        taskPendente.setDescription("Descrição da task pendente");
        taskPendente.setStatus(TaskRole.PENDENTE);
        taskPendente.setUser(usuario);
        em.persist(taskPendente);

        Tasks taskConcluida = new Tasks();
        taskConcluida.setTitle("Task Concluída");
        taskConcluida.setDescription("Descrição da task concluída");
        taskConcluida.setStatus(TaskRole.CONCLUIDA);
        taskConcluida.setUser(usuario);
        em.persist(taskConcluida);

        em.flush();

        // ACT — chama o query method
        List<Tasks> pendentes = taskRepository.findByStatus(TaskRole.PENDENTE);

        // ASSERT — só a pendente deve aparecer
        assertThat(pendentes).hasSize(1);
        assertThat(pendentes.get(0).getTitle()).isEqualTo("Task Pendente");
    }

    // ── Teste 2: findByUserEmail ─────────────────────────────────────────
    @Test
    void deveRetornarTasksDoUsuarioPorEmail() {
        // ARRANGE
        Tasks task = new Tasks();
        task.setTitle("Task do José");
        task.setDescription("Descrição qualquer aqui");
        task.setStatus(TaskRole.PENDENTE);
        task.setUser(usuario);
        em.persist(task);
        em.flush();

        // ACT
        List<Tasks> resultado = taskRepository.findByUserEmail("jose@gmail.com");

        // ASSERT
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getUser().getEmail()).isEqualTo("jose@gmail.com");
    }

    // ── Teste 3: findByUserEmail — usuário sem tasks ──────────────────────
    @Test
    void deveRetornarListaVaziaQuandoUsuarioNaoTemTasks() {
        // ACT — email existe mas não tem tasks
        List<Tasks> resultado = taskRepository.findByUserEmail("jose@gmail.com");

        // ASSERT
        assertThat(resultado).isEmpty();
    }

    // ── Teste 4: @Query customizada — buscarPorTitulo ─────────────────────
    @Test
    void deveBuscarTaskPorTrechoDoTitulo() {
        // ARRANGE
        Tasks task = new Tasks();
        task.setTitle("Implementar tela de login");
        task.setDescription("Criar a interface de autenticação");
        task.setStatus(TaskRole.EM_ANDAMENTO);
        task.setUser(usuario);
        em.persist(task);
        em.flush();

        // ACT — busca por trecho, ignorando maiúsculas/minúsculas
        List<Tasks> resultado = taskRepository.buscarPorTitulo("LOGIN");

        // ASSERT
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTitle()).containsIgnoringCase("login");
    }

    // ── Teste 5: @Query customizada — sem resultado ───────────────────────
    @Test
    void deveRetornarVazioQuandoTituloNaoExiste() {
        // ACT
        List<Tasks> resultado = taskRepository.buscarPorTitulo("xyzabcnaoexiste");

        // ASSERT
        assertThat(resultado).isEmpty();
    }
}