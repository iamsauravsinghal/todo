package com.saurav.task.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.saurav.task.entity.TaskEntity;
import com.saurav.task.model.Task;

@Repository(value="taskDao")
public class TaskDaoImpl implements TaskDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Integer addTaskItem(Task task) {
		TaskEntity taskEntity= new TaskEntity();
			taskEntity.setTaskName(task.getTaskName());
			taskEntity.setExpectedDate(task.getExpectedDate());
			taskEntity.setPriority(task.getPriority());
			entityManager.persist(taskEntity);
			return taskEntity.getId();
	}
	
	
		
	@Override
	public Task updateItem(Task task) {
		TaskEntity taskEntity = entityManager.find(TaskEntity.class, task.getId());
		if(taskEntity!=null) {
			if(task.getExpectedDate()!=null)
				taskEntity.setExpectedDate(task.getExpectedDate());
			if(task.getPriority()!=0)
				taskEntity.setPriority(task.getPriority());
			if(task.getTaskName()!=null)
				taskEntity.setTaskName(task.getTaskName());
			Task task1 =new Task();
			task1.setId(taskEntity.getId());
			task1.setExpectedDate(taskEntity.getExpectedDate());
			task1.setPriority(taskEntity.getPriority());
			task1.setTaskName(taskEntity.getTaskName());
			return task;
		}
		return null;
	}

	@Override
	public List<Task> getTasks(String searchText) {
		String queryString="SELECT e FROM TaskEntity e WHERE e.taskName like '%"+searchText+"%' ORDER BY e.priority DESC";
		Query query = entityManager.createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<TaskEntity> taskEntities= query.getResultList();
		List<Task> tasks = new ArrayList<>();
		if(!taskEntities.isEmpty()) {
			for(TaskEntity taskEntity:taskEntities) {
				Task task = new Task();
				task.setId(taskEntity.getId());
				task.setExpectedDate(taskEntity.getExpectedDate());
				task.setPriority(taskEntity.getPriority());
				task.setTaskName(taskEntity.getTaskName());
				tasks.add(task);
			}
		}
		return tasks;
	}

	@Override
	public Integer closeTaskItem(Integer id) {
		TaskEntity taskEntity = entityManager.find(TaskEntity.class, id);
		if(taskEntity!=null) {
			entityManager.remove(taskEntity);
			return 1;
		}
		return 0;
	}
}
