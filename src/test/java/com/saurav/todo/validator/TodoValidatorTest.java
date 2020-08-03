package com.saurav.todo.validator;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.saurav.exception.InvalidExpectedDateException;
import com.saurav.exception.InvalidPriorityException;
import com.saurav.todo.model.Todo;

 class TodoValidatorTest {
	
	@Test
	void invalidPriorityTest() throws Exception{
		Integer priority = 5;
		Assertions.assertFalse(TodoValidator.validatePriority(priority));
	}
	
	@Test
	void validPriorityTest() throws Exception{
		Integer priority = 1;
		Assertions.assertTrue(TodoValidator.validatePriority(priority));
	}
	
	@Test
	void invalidExpectedDateTest() throws Exception{
		LocalDate expectedDate = LocalDate.now().minusDays(1);
		Assertions.assertFalse(TodoValidator.validateDate(expectedDate));
	}
	
	@Test
	void validExpectedDateTest() throws Exception{
		LocalDate expectedDate = LocalDate.now();
		Assertions.assertTrue(TodoValidator.validateDate(expectedDate));
	}
	
	@Test
	void validateTodoInvalidPriority() throws Exception{
		Todo todo = new Todo();
		todo.setPriority(8);
		todo.setExpectedDate(LocalDate.now());
		Assertions.assertThrows(InvalidPriorityException.class,()->TodoValidator.validateTodo(todo));
	}
	
	@Test
	void validateTodoInvalidExpectedDate() throws Exception{
		Todo todo = new Todo();
		todo.setPriority(2);
		todo.setExpectedDate(LocalDate.now().minusDays(1));
		Assertions.assertThrows(InvalidExpectedDateException.class,()->TodoValidator.validateTodo(todo));
	}
}
