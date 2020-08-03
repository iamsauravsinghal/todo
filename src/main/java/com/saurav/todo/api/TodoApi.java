package com.saurav.todo.api;

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

import com.saurav.todo.model.Todo;
import com.saurav.todo.service.TodoService;

@CrossOrigin
@RestController
@RequestMapping("/")
public class TodoApi {
	
	@Autowired
	private TodoService todoService;
	
	@Autowired
	private Environment environment;

	
	@GetMapping(value="/todo/{searchText}")
	public ResponseEntity<List<Todo>> getTodos(@PathVariable String searchText){
		try {
			List<Todo> todos = todoService.getTodos(searchText);
			return new ResponseEntity<>(todos,HttpStatus.OK);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}
	
	@PostMapping(value="/todo")
	public ResponseEntity<String> addTodoItem(@RequestBody Todo todo){
		try {
			Integer id = todoService.addTodoItem(todo);
			String sucesssMessage = "Todo item created successfully with ID: "+id;
			return new  ResponseEntity<>(sucesssMessage, HttpStatus.CREATED);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, environment.getProperty(e.getMessage()));
		}
	}
	
	@PutMapping(value="/todo")
	public ResponseEntity<Todo> updateItem(@RequestBody Todo todo){
		try {
			Todo todoResult = todoService.updateItem(todo);
			return new ResponseEntity<>(todoResult, HttpStatus.OK);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
	}
	
	@DeleteMapping(value="/todo/{id}")
	public ResponseEntity<String> deleteTodoItem(@PathVariable Integer id){
		try {
			todoService.deleteTodoItem(id);
			String successMessage = "Todo item deleted successfully";
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
	}
	

}
