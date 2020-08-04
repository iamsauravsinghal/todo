package com.saurav.task.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.saurav.task.model.Task;
import com.saurav.task.service.TaskService;

@CrossOrigin
@RestController
@RequestMapping("/")
public class TaskApi {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private Environment environment;

	
	@GetMapping(value="/task/{searchText}")
	public List<Task> getTasks(@PathVariable String searchText){
			List<Task> tasks = taskService.getTasks(searchText);
			return tasks;
	}
	
	@PostMapping(value="/task")
	public HttpStatus addTaskItem(@RequestBody Task task){
			Integer id = taskService.addTaskItem(task);
			return HttpStatus.CREATED;
	}
	
	@PutMapping(value="/task")
	public ResponseEntity<Task> updateItem(@RequestBody Task task){
		try {
			Task taskResult = taskService.updateItem(task);
			return new ResponseEntity<>(taskResult, HttpStatus.OK);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
	}
	
	@DeleteMapping(value="/task/{id}")
	public HttpStatus closeTaskItem(@PathVariable Integer id){
			taskService.closeTaskItem(id);
			String successMessage = "Task item closed successfully";
			return HttpStatus.OK;
	}
	

}
