package com.saurav.task.validator;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.saurav.task.exception.InvalidExpectedDateException;
import com.saurav.task.exception.InvalidPriorityException;
import com.saurav.task.exception.ServiceException;
import com.saurav.task.model.Task;

 class TaskValidatorTest {
	
	@Test
	void invalidPriorityTest() throws Exception{
		Integer priority = 5;
		Assertions.assertFalse(TaskValidator.validatePriority(priority));
	}
	
	@Test
	void validPriorityTest() throws Exception{
		Integer priority = 1;
		Assertions.assertTrue(TaskValidator.validatePriority(priority));
	}
	
	@Test
	void invalidExpectedDateTest() throws Exception{
		LocalDate expectedDate = LocalDate.now().minusDays(1);
		Assertions.assertFalse(TaskValidator.validateDate(expectedDate));
	}
	
	@Test
	void validExpectedDateTest() throws Exception{
		LocalDate expectedDate = LocalDate.now();
		Assertions.assertTrue(TaskValidator.validateDate(expectedDate));
	}
	
	@Test
	void validateTaskInvalidPriority() throws Exception{
		Task task = new Task();
		task.setPriority(8);
		task.setExpectedDate(LocalDate.now());
		Assertions.assertThrows(ServiceException.class,()->TaskValidator.validateTask(task));
	}
	
	@Test
	void validateTaskInvalidExpectedDate() throws Exception{
		Task task = new Task();
		task.setPriority(2);
		task.setExpectedDate(LocalDate.now().minusDays(1));
		Assertions.assertThrows(ServiceException.class,()->TaskValidator.validateTask(task));
	}
}
