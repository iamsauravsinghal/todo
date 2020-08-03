package com.saurav.todo.service;

import java.util.List;

import com.saurav.exception.AddFailedException;
import com.saurav.exception.DeleteFailedException;
import com.saurav.exception.InvalidExpectedDateException;
import com.saurav.exception.InvalidPriorityException;
import com.saurav.exception.NoResultFoundException;
import com.saurav.exception.UpdateFailedException;
import com.saurav.todo.model.Todo;


public interface TodoService {
	Integer addTodoItem(Todo todo) throws InvalidPriorityException, InvalidExpectedDateException, AddFailedException;
	Integer deleteTodoItem(Integer id)throws DeleteFailedException;
	Todo updateItem(Todo todo)throws UpdateFailedException,InvalidPriorityException, InvalidExpectedDateException;
	List<Todo> getTodos(String searchText)throws NoResultFoundException;
}

