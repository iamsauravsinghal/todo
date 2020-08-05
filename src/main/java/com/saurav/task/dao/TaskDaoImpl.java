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
		if(task.getParentId()!=null) {
			TaskEntity parentEntity = entityManager.find(TaskEntity.class, task.getParentId());
			if(parentEntity!=null) {
				List<TaskEntity> taskEntities = parentEntity.getTaskEntities();
				if(taskEntities==null)
					taskEntities =new ArrayList<>();
					if(parentEntity.getParentId()==null) {
						TaskEntity subTaskEntity= new TaskEntity();
						subTaskEntity.setTaskName(task.getTaskName());
						subTaskEntity.setExpectedDate(task.getExpectedDate());
						subTaskEntity.setPriority(task.getPriority());
						subTaskEntity.setStatus(task.getStatus());
						subTaskEntity.setParentId(task.getParentId());
						entityManager.persist(subTaskEntity);
						taskEntities.add(subTaskEntity);
						parentEntity.setTaskEntities(taskEntities);
						System.out.println("Reaching: "+subTaskEntity.getId());
						return subTaskEntity.getId();
					}
					else {
						return -1;
					}
			}
			else {
				return -2;
			} 
		}
		else {
			TaskEntity taskEntity= new TaskEntity();
			taskEntity.setTaskName(task.getTaskName());
			taskEntity.setExpectedDate(task.getExpectedDate());
			taskEntity.setPriority(task.getPriority());
			taskEntity.setStatus(task.getStatus());
			taskEntity.setParentId(task.getParentId());
			taskEntity.setTaskEntities(new LinkedList<TaskEntity>());
			entityManager.persist(taskEntity);
			return taskEntity.getId();
		}
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
	public List<Task> getTasks(String searchText) {
		String queryString="SELECT e FROM TaskEntity e WHERE e.parentId IS NULL AND e.taskName like '%"+searchText+"%' ORDER BY e.priority DESC";
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
				task.setStatus(taskEntity.getStatus());
				List<Task> subTasks = new ArrayList<>();
				List<TaskEntity> subTaskEntities = taskEntity.getTaskEntities();
				if(!subTaskEntities.isEmpty()) {
					for(TaskEntity subTaskEntity:subTaskEntities) {
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
				tasks.add(task);
			}
		}
		return tasks;
	}

	@Override
	public Integer closeTaskItem(Integer id) {
		TaskEntity taskEntity = entityManager.find(TaskEntity.class, id);
		if(taskEntity!=null) {
			taskEntity.setStatus("closed");
			List<TaskEntity> subTaskEntities = taskEntity.getTaskEntities();
			if(!subTaskEntities.isEmpty()) {
				for(TaskEntity subTaskEntity:subTaskEntities) {
					subTaskEntity.setStatus("closed");
				}
				taskEntity.setTaskEntities(subTaskEntities);
			}
			if(taskEntity.getParentId()!=null) {
				TaskEntity parentTaskEntity = entityManager.find(TaskEntity.class, taskEntity.getParentId());
				boolean flag=false;
				if(parentTaskEntity!=null) {
					List<TaskEntity> parentTaskEntities=parentTaskEntity.getTaskEntities();
					System.out.println(parentTaskEntities.size());
					for(TaskEntity subTaskEntities1:parentTaskEntities) {
						System.out.println(subTaskEntities1.getStatus());
						if(!subTaskEntities1.getStatus().equals("closed"))
						{
							flag=true;
							break;
						}
					}
					System.out.println(flag);
					if(flag==false)
						parentTaskEntity.setStatus("closed");
				}
			}
			return 1;
		}
		return 0;
	}
	
//	@Override
//	public Integer closeTaskItem(Integer id) {
//		TaskEntity taskEntity = entityManager.find(TaskEntity.class, id);
//		if(taskEntity!=null) {
//			entityManager.remove(taskEntity);
//			return 1;
//		}
//		return 0;
//	}
}
