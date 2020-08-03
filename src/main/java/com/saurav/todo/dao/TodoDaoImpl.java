package com.saurav.todo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.saurav.todo.entity.TodoEntity;
import com.saurav.todo.model.Todo;

@Repository(value="todoDao")
public class TodoDaoImpl implements TodoDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Integer addTodoItem(Todo todo) {
		TodoEntity todoEntity= new TodoEntity();
			todoEntity.setTaskName(todo.getTaskName());
			todoEntity.setExpectedDate(todo.getExpectedDate());
			todoEntity.setPriority(todo.getPriority());
			entityManager.persist(todoEntity);
			return todoEntity.getId();
	}
	
	@Override
	public Integer deleteTodoItem(Integer id) {
		TodoEntity todoEntity = entityManager.find(TodoEntity.class, id);
		if(todoEntity!=null) {
			entityManager.remove(todoEntity);
			return 1;
		}
		return 0;
	}
		
	@Override
	public Todo updateItem(Todo todo) {
		TodoEntity todoEntity = entityManager.find(TodoEntity.class, todo.getId());
		if(todoEntity!=null) {
			if(todo.getExpectedDate()!=null)
				todoEntity.setExpectedDate(todo.getExpectedDate());
			if(todo.getPriority()!=0)
				todoEntity.setPriority(todo.getPriority());
			if(todo.getTaskName()!=null)
				todoEntity.setTaskName(todo.getTaskName());
			Todo todo1 =new Todo();
			todo1.setId(todoEntity.getId());
			todo1.setExpectedDate(todoEntity.getExpectedDate());
			todo1.setPriority(todoEntity.getPriority());
			todo1.setTaskName(todoEntity.getTaskName());
			return todo;
		}
		return null;
	}

	@Override
	public List<Todo> getTodos(String searchText) {
		String queryString="SELECT e FROM TodoEntity e WHERE e.taskName like '%"+searchText+"%' ORDER BY e.priority DESC";
		Query query = entityManager.createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<TodoEntity> todoEntities= query.getResultList();
		List<Todo> todos = new ArrayList<>();
		if(!todoEntities.isEmpty()) {
			for(TodoEntity todoEntity:todoEntities) {
				Todo todo = new Todo();
				todo.setId(todoEntity.getId());
				todo.setExpectedDate(todoEntity.getExpectedDate());
				todo.setPriority(todoEntity.getPriority());
				todo.setTaskName(todoEntity.getTaskName());
				todos.add(todo);
			}
		}
		return todos;
	}
}
