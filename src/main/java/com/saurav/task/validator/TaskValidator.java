package com.saurav.task.validator;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import com.saurav.task.exception.ServiceException;
import com.saurav.task.model.Task;

public class TaskValidator {
	private TaskValidator() {}
	
	public static void validateTask(Task task){
		if(!validatePriority(task.getPriority()))
			throw new ServiceException("VALIDATOR.invalid_priority_value", HttpStatus.NOT_ACCEPTABLE);
		if(!validateDate(task.getExpectedDate()))
			throw new ServiceException("VALIDATOR.invalid_expected_date", HttpStatus.NOT_ACCEPTABLE);
	}
	
	public static boolean validatePriority(Integer priority) {
		return priority<4 && priority>0;
	}
	
	public static boolean validateDate(LocalDate expectedDate){
		LocalDate yesterday= LocalDate.now().minusDays(1);
		return expectedDate.isAfter(yesterday);
	}
}
