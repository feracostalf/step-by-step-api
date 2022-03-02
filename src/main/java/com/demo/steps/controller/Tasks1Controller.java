package com.demo.steps.controller;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.demo.steps.entities.Task;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/tasks")
public class Tasks1Controller {
	private final List<Task> taskList=new ArrayList<>();

    @GetMapping("")
	public ResponseEntity<List<Task>> fetchAllTask(){
		return ResponseEntity.ok().body(taskList);
	}

	@PostMapping("")
	public ResponseEntity<Task> postTask(@RequestBody Task newTask){
		newTask.setActive(false);
		newTask.setCreatedAt(LocalDateTime.now());
		long id= taskList.size()+1;
		newTask.setId(id);
		taskList.add(newTask);

		return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

	@GetMapping("/{taskId}")
	public ResponseEntity<Task> fetchTaskById(@PathVariable("taskId") Long taskId){
		return taskList
			.stream()
			.filter(current->taskId==current.getId())
			.findFirst()
			.map(task-> ResponseEntity.ok().body(task))
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}


	@DeleteMapping("/{taskId}]")
	public ResponseEntity<?> deleteTask(@PathVariable("taskId") Long taskId){
		return taskList
			.stream()
			.filter(current->taskId==current.getId())
			.findFirst()
			.map(task-> {
				taskList.remove(task);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			})
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	
	@PutMapping("/{taskId}]")
	public ResponseEntity<Task> putTask(@PathVariable("taskId") Long taskId,@RequestBody Task updatedTask){
		return taskList
			.stream()
			.filter(current->taskId==current.getId())
			.findFirst()
			.map(task-> {
				task.setTitle(updatedTask.getTitle());
				task.setDescription(updatedTask.getDescription());
				return ResponseEntity.ok().body(task);
			})
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
    
}
