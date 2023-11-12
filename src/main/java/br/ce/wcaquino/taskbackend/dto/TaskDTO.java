package br.ce.wcaquino.taskbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO {
    private String description;
    private LocalDate dueDate;
}
