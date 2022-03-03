package com.demo.steps.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.demo.steps.entities.Task;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class TaskS2Service {
    private final List<Task> taskList=new ArrayList<>();

	public List<Task> getAllTask(){
		return taskList;
	}


	public Task saveTask( Task newTask){
		newTask.setActive(false);
		newTask.setCreatedAt(LocalDateTime.now());
		long id= taskList.size()+1;
		newTask.setId(id);
		taskList.add(newTask);

		return newTask;
    }

    
	public Optional <Task> getTaskById( Long taskId){
		return taskList
			.stream()
			.filter(current->taskId==current.getId())
			.findFirst()
			.map(task-> task);
	}

    public boolean deleteTask( Long taskId){
		return taskList
			.stream()
			.filter(current->taskId==current.getId())
			.findFirst()
			.map(task-> {
				taskList.remove(task);
				return true;
			})
			.orElse(false);
	}

	
	public Optional<Task> updateTask(Long taskId, Task updatedTask){
		return taskList
			.stream()
			.filter(current->taskId==current.getId())
			.findFirst()
			.map(task-> {
				task.setTitle(updatedTask.getTitle());
				task.setDescription(updatedTask.getDescription());
				return task;
			});
	}

}

