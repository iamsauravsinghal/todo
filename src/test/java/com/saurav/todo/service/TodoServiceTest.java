package com.saurav.todo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.saurav.exception.DeleteFailedException;
import com.saurav.exception.InvalidExpectedDateException;
import com.saurav.exception.InvalidPriorityException;
import com.saurav.exception.NoResultFoundException;
import com.saurav.exception.UpdateFailedException;
import com.saurav.todo.dao.TodoDao;
import com.saurav.todo.model.Todo;


@SpringBootTest
@Transactional
@Rollback(true)
class TodoServiceTest {
	@Mock
	TodoDao todoDao;
	
	@InjectMocks
	TodoServiceImpl todoService;
	
	@Test
	void resultFound() throws Exception{
		String searchText="A";
		List<Todo> todos = new ArrayList<>();
		Todo todo =new Todo();
		todos.add(todo);
		when(this.todoDao.getTodos(anyString())).thenReturn(todos);
		Assertions.assertFalse(todoService.getTodos(searchText).isEmpty());
	}
	
	@Test
	void resultNotFound() throws Exception{
		String searchText= "Manage";
		when(this.todoDao.getTodos(anyString())).thenReturn(new ArrayList<Todo>());
		Assertions.assertThrows(NoResultFoundException.class,()->this.todoService.getTodos(searchText));
	}
	
	@Test
	void validAddTodo() throws Exception{
		Todo todo = new Todo();
		todo.setPriority(2);
		todo.setExpectedDate(LocalDate.now());
		when(this.todoDao.addTodoItem(todo)).thenReturn(1);
		Assertions.assertTrue(todoService.addTodoItem(todo)>0);
	}
	
	@Test
	void invalidPriorityAddTodo() throws Exception{
		Todo todo = new Todo();
		todo.setPriority(5);
		todo.setExpectedDate(LocalDate.now());
		when(this.todoDao.addTodoItem(todo)).thenReturn(1);
		Assertions.assertThrows(InvalidPriorityException.class, ()->this.todoService.addTodoItem(todo));
	}
	
	@Test
	void invalidExpectedDateAddTodo() throws Exception{
		Todo todo = new Todo();
		todo.setPriority(3);
		todo.setExpectedDate(LocalDate.now().minusDays(2));
		when(this.todoDao.addTodoItem(todo)).thenReturn(1);
		Assertions.assertThrows(InvalidExpectedDateException.class, ()->this.todoService.addTodoItem(todo));
	}
		
	@Test
	void invalidTodoUpdate() throws Exception{
		Todo todo = new Todo();
		when(this.todoDao.updateItem(todo)).thenReturn(null);	
		Assertions.assertThrows(UpdateFailedException.class, ()->this.todoService.updateItem(todo));
	}
	
	@Test
	void invalidPriorityTodoUpdate() throws Exception{
		Todo todo = new Todo();
		todo.setPriority(5);
		todo.setExpectedDate(LocalDate.now());
		when(this.todoDao.updateItem(todo)).thenReturn(todo);	
		Assertions.assertThrows(InvalidPriorityException.class, ()->this.todoService.updateItem(todo));
	}
	
	@Test
	void invalidExpectedDateTodoUpdate() throws Exception{
		Todo todo = new Todo();
		todo.setPriority(3);
		todo.setExpectedDate(LocalDate.now().minusDays(2));
		when(this.todoDao.updateItem(todo)).thenReturn(todo);	
		Assertions.assertThrows(InvalidExpectedDateException.class, ()->this.todoService.updateItem(todo));
	}
	
	@Test
	void validTodoUpdate() throws Exception{
		Todo todo = new Todo();
		todo.setPriority(2);
		todo.setExpectedDate(LocalDate.now());
		when(this.todoDao.updateItem(todo)).thenReturn(todo);	
		Assertions.assertNotNull(todoService.updateItem(todo));
	}
	
	@Test
	void validTodoDelete() throws Exception{
		Integer id=1;
		when(this.todoDao.deleteTodoItem(anyInt())).thenReturn(1);
		Assertions.assertEquals(1,todoService.deleteTodoItem(id));
	}
	
	@Test
	void invalidTodoDelete() throws Exception{
		Integer id=0;
		when(this.todoDao.deleteTodoItem(anyInt())).thenReturn(0);
		Assertions.assertThrows(DeleteFailedException.class,()-> this.todoService.deleteTodoItem(id));
	}
}
