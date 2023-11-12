import br.ce.wcaquino.taskbackend.controller.TaskController;
import br.ce.wcaquino.taskbackend.dto.TaskDTO;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void naoDeveSalvarTarefaSemDescricao() {
        TaskDTO todo = new TaskDTO();
        todo.setDueDate(LocalDate.now());
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> controller.save(todo));
        Assertions.assertEquals("Fill the task description", validationException.getMessage());
    }

    @Test
    void naoDeveSalvarTarefaComDescricaoVazia() {
        TaskDTO todo = new TaskDTO();
        todo.setDescription("");
        todo.setDueDate(LocalDate.now());
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> controller.save(todo));
        Assertions.assertEquals("Fill the task description", validationException.getMessage());
    }

    @Test
    void naoDeveSalvarTarefaComDescricaoApenasComEspacos() {
        TaskDTO todo = new TaskDTO();
        todo.setDescription("   ");
        todo.setDueDate(LocalDate.now());
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> controller.save(todo));
        Assertions.assertEquals("Fill the task description", validationException.getMessage());
    }

    @Test
    void naoDeveSalvarTarefaSemData() {
        TaskDTO todo = new TaskDTO();
        todo.setDescription("Description");
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> controller.save(todo));
        Assertions.assertEquals("Fill the due date", validationException.getMessage());
    }

    @Test
    void naoDeveSalvarTarefaComDataPassada() {
        TaskDTO todo = new TaskDTO();
        todo.setDescription("Description");
        todo.setDueDate(LocalDate.of(2010, 1, 1));
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> controller.save(todo));
        Assertions.assertEquals("Due date must not be in past", validationException.getMessage());
    }

    @Test
    void deveSalvarTarefaComSucesso() throws ValidationException {
        TaskDTO todo = new TaskDTO();
        todo.setDescription("Description");
        todo.setDueDate(LocalDate.now());
        controller.save(todo);
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        Mockito.verify(taskRepo, Mockito.times(1)).save(taskCaptor.capture());
    }
}
