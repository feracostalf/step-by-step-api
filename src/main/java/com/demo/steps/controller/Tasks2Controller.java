package com.demo.steps.controller;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.demo.steps.entities.Task;
import com.demo.steps.service.TaskS2Service;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/steps/02/tasks")
public class Tasks2Controller {

	private final TaskS2Service service;

	public Tasks2Controller(@Autowired TaskS2Service service){
		this.service=service;
	}

    @GetMapping("")
	public ResponseEntity<List<Task>> fetchAllTask(){
		return ResponseEntity.ok().body(service.getAllTask());
	}

	@PostMapping("")
	public ResponseEntity<Task> postTask(@RequestBody Task newTask){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.saveTask(newTask));
    }

	
	@GetMapping("/{taskId}")
	public ResponseEntity<Task> fetchTaskById(@PathVariable("taskId") Long taskId){
		return service.getTaskById(taskId)
			.map(task-> ResponseEntity.ok().body(task))
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}


	@DeleteMapping("/{taskId}]")
	public ResponseEntity<?> deleteTask(@PathVariable("taskId") Long taskId){
		boolean isDeleted=service.deleteTask(taskId);
		if(isDeleted){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	
	}

	
	@PutMapping("/{taskId}]")
	public ResponseEntity<Task> putTask(@PathVariable("taskId") Long taskId,@RequestBody Task updatedTask){
		return service.updateTask(taskId,updatedTask)
			.map(task-> {
				return ResponseEntity.ok().body(task);
			})
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
    
}
