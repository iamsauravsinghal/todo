package com.saurav.task.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.saurav.task.model.Task;
import com.saurav.task.service.TaskService;

@CrossOrigin
@RestController
@RequestMapping("/")
public class TaskApi {
	
	@Autowired
	private TaskService taskService;
	
	
	@GetMapping(value="/task/{searchText}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Task> getTasks(@PathVariable String searchText){
			return taskService.getTasks(searchText);
	}
	
	@PostMapping(value="/task")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String addTaskItem(@RequestBody Task task){
		Integer id = taskService.addTaskItem(task);
			return "Task created successfully with id:"+id;
	}
	
	@PutMapping(value="/task")
	@ResponseStatus(code = HttpStatus.OK)
	public Task updateItem(@RequestBody Task task){
			return taskService.updateItem(task);
	}
	
	@PutMapping(value="/task/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public String closeTaskItem(@PathVariable Integer id){
			taskService.closeTaskItem(id);
			return "Task item closed successfully";
	}
	
	@DeleteMapping(value="/task/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public String deleteTaskItem(@PathVariable Integer id){
			taskService.deleteTaskItem(id);
			return "Task item deleted successfully";
	}
	

}
