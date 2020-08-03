package com.saurav.todo.validator;

import java.time.LocalDate;

import com.saurav.exception.InvalidExpectedDateException;
import com.saurav.exception.InvalidPriorityException;
import com.saurav.todo.model.Todo;

public class TodoValidator {
	private TodoValidator() {}
	
	public static void validateTodo(Todo todo) throws InvalidPriorityException, InvalidExpectedDateException{
		if(!validatePriority(todo.getPriority()))
			throw new InvalidPriorityException("VALIDATOR.invalid_priority_value");
		if(!validateDate(todo.getExpectedDate()))
			throw new InvalidExpectedDateException("VALIDATOR.invalid_expected_date");
	}
	
	public static boolean validatePriority(Integer priority) {
		return priority<4 && priority>0;
	}
	
	public static boolean validateDate(LocalDate expectedDate){
		LocalDate yesterday= LocalDate.now().minusDays(1);
		return expectedDate.isAfter(yesterday);
	}
}
