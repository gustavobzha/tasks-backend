package br.ce.wcaquino.taskbackend.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Task {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private LocalDate dueDate;
	
}
