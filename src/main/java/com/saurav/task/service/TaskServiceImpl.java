package com.saurav.task.service;

import java.util.LinkedList;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.saurav.task.dao.TaskDao;
import com.saurav.task.exception.ServiceException;
import com.saurav.task.model.Task;
import com.saurav.task.validator.TaskValidator;

@Service(value="taskService")
@Transactional
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskDao taskDao;

	@Override
	public Integer addTaskItem(Task task){
		if(task.getParentId()!=null && taskDao.getTaskById(task.getParentId())==null)
			throw new ServiceException("SERVICE.parent_not_exist", HttpStatus.EXPECTATION_FAILED);
		TaskValidator.validateTask(task);
		Integer result;
		try {
		if(task.getSubTasks()==null)
			task.setSubTasks(new LinkedList<>());
		result=taskDao.addTaskItem(task);
		if(!task.getSubTasks().isEmpty()) {
			List<Task> subTasks = task.getSubTasks();
			for(Task subTask: subTasks) {
				subTask.setParentId(result);
				this.addTaskItem(subTask);
			}
		}
		}
		catch(Exception e) {
			throw new ServiceException("SERVICE.add_failed", HttpStatus.EXPECTATION_FAILED);
		}
		return result;
	}
	
	private void closeTaskAndSubtasks(Integer id) {

		Task task=taskDao.getTaskById(id);
		taskDao.closeTaskItem(task.getId());
		if(task.getSubTasks()==null)
			task.setSubTasks(new LinkedList<>());
		List<Task> subTasks = task.getSubTasks();
		if(!subTasks.isEmpty())
			for(Task subTask:subTasks)
				this.closeTaskItem(subTask.getId());
	}
	
	void closeParentTasks(Integer id) {
		Task parentTask =taskDao.getTaskById(id);
		List<Task> subTasks = parentTask.getSubTasks();
		for(Task subTask:subTasks) {
			if(!subTask.getStatus().equals("closed"))
				return;
		}
		taskDao.closeTaskItem(id);
		if(parentTask.getParentId()!=null)
			this.closeParentTasks(parentTask.getParentId());
	}
	
	@Override
	public void closeTaskItem(Integer id){
		try {
		closeTaskAndSubtasks(id);
		
		Task task=taskDao.getTaskById(id);
		if(task.getParentId()!=null)
			closeParentTasks(task.getParentId());
		}
		catch(Exception e) {
			throw new ServiceException("SERVICE.close_failed", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public Task updateItem(Task task){
		TaskValidator.validateTask(task);
		Task taskResult=taskDao.updateItem(task);
		if(taskResult==null) {
			throw new ServiceException("SERVICE.nothing_updated", HttpStatus.BAD_REQUEST);
		}
		return taskResult;
	}
	
	private void getTasksAndSubtasks(List<Task> tasks, Integer id) {
		Task task = taskDao.getTaskById(id);
		
		if(task.getSubTasks()!=null&& !task.getSubTasks().isEmpty()) {
			List<Task> subTasks = new LinkedList<>();
			for(Task subTask:task.getSubTasks())
				getTasksAndSubtasks(subTasks, subTask.getId());
			task.setSubTasks(subTasks);
		}
		tasks.add(task);
	}
	
	@Override
	public List<Task> getTasks(String searchText){
		List<Integer> results=taskDao.getTasks(searchText);
		if(results.isEmpty()) {
			throw new ServiceException("SERVICE.no_result_found",HttpStatus.NOT_FOUND);
		}
		List<Task> tasks =new LinkedList<>();
		for(Integer id:results)
			this.getTasksAndSubtasks(tasks, id);
		return tasks;
	}
	
	@Override
	public Integer deleteTaskItem(Integer id) {
		Integer result=taskDao.deleteTaskItem(id);
		if(result==0)
			throw new ServiceException("SERVICE.delete_failed", HttpStatus.BAD_REQUEST);
		return result;
	}
	
}
