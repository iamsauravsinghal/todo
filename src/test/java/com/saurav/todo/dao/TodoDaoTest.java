package com.saurav.todo.dao;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.saurav.todo.model.Todo;

@SpringBootTest
@Transactional
@Rollback(true)
class TodoDaoTest {
	
	@Autowired
	private TodoDao todoDao;
	
	@Test
	void invalidTodoUpdate() throws Exception{
		Todo todo = new Todo();
		todo.setId(-1);
		todo.setPriority(2);
		todo.setTaskName("Test Task");
		todo.setExpectedDate(LocalDate.now());
		Assertions.assertNull(todoDao.updateItem(todo));
	}
	
	@Test
	void validTodoUpdate() throws Exception{
		Todo todo = new Todo();
		todo.setId(1);
		todo.setPriority(2);
		todo.setTaskName("Test Task");
		todo.setExpectedDate(LocalDate.now());
		Assertions.assertNotNull(todoDao.updateItem(todo));
	}
	
	@Test
	void validAddTodo() throws Exception{
		Todo todo = new Todo();
		todo.setPriority(8);
		todo.setExpectedDate(LocalDate.now());
		todo.setTaskName("Test Task");
		Assertions.assertTrue(todoDao.addTodoItem(todo)>0);
	}
	
	@Test
	void validTodoDelete() throws Exception{
		Integer id = 1;
		Assertions.assertEquals(1,todoDao.deleteTodoItem(id));
	}
	
	@Test
	void invalidTodoDelete() throws Exception{
		Integer id = 0;
		Assertions.assertEquals(0,todoDao.deleteTodoItem(id));
	}
	
	@Test
	void resultFound() throws Exception{
		String searchText= "A";
		Assertions.assertFalse(todoDao.getTodos(searchText).isEmpty());
	}
	
	@Test
	void resultNotFound() throws Exception{
		String searchText= "Manage";
		Assertions.assertTrue(todoDao.getTodos(searchText).isEmpty());
	}
	
}
