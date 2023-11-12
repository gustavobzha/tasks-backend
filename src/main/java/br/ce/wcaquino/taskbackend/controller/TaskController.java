package br.ce.wcaquino.taskbackend.controller;

import java.util.List;

import br.ce.wcaquino.taskbackend.dto.TaskDTO;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.DateUtils;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

@RestController
@RequestMapping(value ="/todo")
public class TaskController {

	@Autowired
	private TaskRepo todoRepo;
	
	@GetMapping
	public List<Task> findAll() {
		return todoRepo.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Task> save(@RequestBody TaskDTO todo) throws ValidationException {
		if(StringUtils.isEmpty(todo.getDescription()) || todo.getDescription().isBlank()) {
			throw new ValidationException("Fill the task description");
		}
		if(todo.getDueDate() == null) {
			throw new ValidationException("Fill the due date");
		}
		if(!DateUtils.isEqualOrFutureDate(todo.getDueDate())) {
			throw new ValidationException("Due date must not be in past");
		}

		ModelMapper modelMapper = new ModelMapper();
		Task saved = todoRepo.save(modelMapper.map(todo, Task.class));
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
}
