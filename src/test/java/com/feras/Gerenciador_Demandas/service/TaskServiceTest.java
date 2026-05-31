package com.feras.Gerenciador_Demandas.service;

import com.feras.Gerenciador_Demandas.dto.TaskRequestDTO;
import com.feras.Gerenciador_Demandas.model.Tasks;
import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.repository.TaskRepository;
import com.feras.Gerenciador_Demandas.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository tasksRepository; // Nome exato do seu atributo na Service

    @Mock
    private UserRepository userRepository; // Adicionado porque a Service busca o usuário antes de criar a Task

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Deve criar uma task com sucesso quando os dados forem válidos")
    void criarTask_DeveRetornarTaskSalva_QuandoDadosValidos() {
        // 1. Arrange (Cenário de teste)
        String emailMock = "usuario@email.com";

        // Criando o DTO que o método 'criar' exige como segundo parâmetro
        TaskRequestDTO dtoFake = new TaskRequestDTO();
        dtoFake.setTitle("Estudar Testes");
        dtoFake.setDescription("Descrição da atividade");

        // Criando o Usuário fictício que o UserRepository deve retornar
        Users usuarioFake = new Users();
        usuarioFake.setEmail(emailMock);

        // Criando a Task fictícia que o seu repository vai retornar ao final
        Tasks taskFake = new Tasks();
        taskFake.setTitle(dtoFake.getTitle());
        taskFake.setDescription(dtoFake.getDescription());
        taskFake.setUser(usuarioFake);

        // Configurando os comportamentos dos Mocks (Simulações)
        Mockito.when(userRepository.findByEmail(emailMock)).thenReturn(Optional.of(usuarioFake));
        Mockito.when(tasksRepository.save(any(Tasks.class))).thenReturn(taskFake);

        // 2. Act (Ação) - Chamando o método real com os 2 argumentos corretos!
        Tasks taskResultado = taskService.criar(emailMock, dtoFake);

        // 3. Assert (Verificações)
        assertNotNull(taskResultado);
        assertEquals("Estudar Testes", taskResultado.getTitle());

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(emailMock);
        Mockito.verify(tasksRepository, Mockito.times(1)).save(any(Tasks.class));
    }
}