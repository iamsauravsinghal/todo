package com.saurav.task.dao;

import java.util.ArrayList;
import java.util.LinkedList;
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
		taskEntity.setStatus(task.getStatus());
		taskEntity.setParentId(task.getParentId());
		entityManager.persist(taskEntity);
		return taskEntity.getId();
	}
	
	@Override
	public Task getTaskById(Integer id) {
		TaskEntity taskEntity = entityManager.find(TaskEntity.class, id);
		Task task =new Task();
		if(taskEntity!=null) {
			task.setId(taskEntity.getId());
			task.setExpectedDate(taskEntity.getExpectedDate());
			task.setPriority(taskEntity.getPriority());
			task.setTaskName(taskEntity.getTaskName());  
			task.setParentId(taskEntity.getParentId());
			task.setStatus(taskEntity.getStatus());
			String queryString ="SELECT e FROM TaskEntity e WHERE e.parentId = :parentId ORDER BY e.priority DESC, e.status, e.expectedDate";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("parentId", task.getId());
			@SuppressWarnings("unchecked")
			List<TaskEntity> taskEntities= query.getResultList();
			List<Task> subTasks = new LinkedList<>();
			if(taskEntities!=null && !taskEntities.isEmpty()) {
				for(TaskEntity subTaskEntity:taskEntities) {
					Task subTask = new Task();
					subTask.setId(subTaskEntity.getId());
					subTask.setExpectedDate(subTaskEntity.getExpectedDate());
					subTask.setPriority(subTaskEntity.getPriority());
					subTask.setTaskName(subTaskEntity.getTaskName());
					subTask.setStatus(subTaskEntity.getStatus());
					subTask.setParentId(subTaskEntity.getParentId());
					subTasks.add(subTask);
				}
				task.setSubTasks(subTasks);
			}
		}
		return task;
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
			if(task.getParentId()!=null)
				taskEntity.setParentId(task.getParentId());
			if(task.getStatus()!=null)
				taskEntity.setStatus(task.getStatus());
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
	public List<Integer> getTasks(String searchText) {
		String queryString="SELECT e FROM TaskEntity e WHERE e.parentId IS NULL AND e.taskName like '%"+searchText+"%' ORDER BY e.priority DESC, e.status, e.expectedDate";
		Query query = entityManager.createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<TaskEntity> taskEntities= query.getResultList();
		List<Integer> taskIds = new ArrayList<>();
		if(!taskEntities.isEmpty()) {
			for(TaskEntity taskEntity:taskEntities) {
				taskIds.add(taskEntity.getId());
			}
		}
		return taskIds;
	}
	
	@Override
	public void closeTaskItem(Integer id) {
		TaskEntity taskEntity = entityManager.find(TaskEntity.class, id);
		taskEntity.setStatus("closed");
	}
	
	@Override
	public Integer deleteTaskItem(Integer id) {
		TaskEntity taskEntity = entityManager.find(TaskEntity.class, id);
		if(taskEntity!=null) {
			entityManager.remove(taskEntity);
			return 1;
		}
		return 0;
	}
}