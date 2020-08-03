package com.saurav.todo.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saurav.exception.AddFailedException;
import com.saurav.exception.DeleteFailedException;
import com.saurav.exception.InvalidExpectedDateException;
import com.saurav.exception.InvalidPriorityException;
import com.saurav.exception.NoResultFoundException;
import com.saurav.exception.UpdateFailedException;
import com.saurav.todo.dao.TodoDao;
import com.saurav.todo.model.Todo;
import com.saurav.todo.validator.TodoValidator;

@Service(value="todoService")
@Transactional
public class TodoServiceImpl implements TodoService{
	
	@Autowired
	private TodoDao todoDao;

	@Override
	public Integer addTodoItem(Todo todo) throws InvalidPriorityException, InvalidExpectedDateException, AddFailedException{
		try {
		TodoValidator.validateTodo(todo);
		return todoDao.addTodoItem(todo);
		}
		catch(InvalidPriorityException|InvalidExpectedDateException e) {
			throw e;
		}
		catch(Exception e) {
			throw new AddFailedException("SERVICE.add_failed");
		}
	}

	@Override
	public Integer deleteTodoItem(Integer id) throws DeleteFailedException{
		Integer result = todoDao.deleteTodoItem(id);
		if(result==0) {
			throw new DeleteFailedException("SERVICE.delete_failed");
		}
		return result;
	}

	@Override
	public Todo updateItem(Todo todo) throws UpdateFailedException,InvalidPriorityException, InvalidExpectedDateException{
		try {
		TodoValidator.validateTodo(todo);
		Todo todoResult=todoDao.updateItem(todo);
		if(todoResult==null) {
			throw new UpdateFailedException("SERVICE.nothing_updated");
		}
		return todoResult;
		}
		catch(InvalidPriorityException|InvalidExpectedDateException e) {
			throw e;
		}
		catch(Exception e) {
			throw new UpdateFailedException("SERVICE.nothing_updated");
		}
	}

	@Override
	public List<Todo> getTodos(String searchText) throws NoResultFoundException{
		List<Todo> results=todoDao.getTodos(searchText);
		if(results.isEmpty()) {
			throw new NoResultFoundException("SERVICE.no_result_found");
		}
		return results;
	}
	
}
