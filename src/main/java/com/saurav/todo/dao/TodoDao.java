package com.saurav.todo.dao;

import java.util.List;

import com.saurav.todo.model.Todo;

public interface TodoDao{
	Integer addTodoItem(Todo todo);
	Integer deleteTodoItem(Integer id);
	Todo updateItem(Todo todo);
	List<Todo> getTodos(String searchText);
}
