import br.ce.wcaquino.taskbackend.controller.TaskController;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> controller.save(todo));
        Assertions.assertEquals("Fill the task description", validationException.getMessage());
    }

    @Test
    void naoDeveSalvarTarefaSemData() {
        Task todo = new Task();
        todo.setTask("Description");
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> controller.save(todo));
        Assertions.assertEquals("Fill the due date", validationException.getMessage());
    }

    @Test
    void naoDeveSalvarTarefaComDataPassada() {
        Task todo = new Task();
        todo.setTask("Description");
        todo.setDueDate(LocalDate.of(2010, 1, 1));
        ValidationException validationException = Assertions.assertThrows(ValidationException.class, () -> controller.save(todo));
        Assertions.assertEquals("Due date must not be in past", validationException.getMessage());
    }

    @Test
    void deveSalvarTarefaComSucesso() throws ValidationException {
        Task todo = new Task();
        todo.setTask("Description");
        todo.setDueDate(LocalDate.now());
        controller.save(todo);
        Mockito.verify(taskRepo).save(todo);
    }
}
