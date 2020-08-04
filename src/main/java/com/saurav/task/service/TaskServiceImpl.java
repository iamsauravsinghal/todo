package com.saurav.task.service;

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
		TaskValidator.validateTask(task);
		try {
		
		return taskDao.addTaskItem(task);
		}
		catch(Exception e) {
			throw new ServiceException("SERVICE.add_failed", HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public Integer closeTaskItem(Integer id){
		Integer result = taskDao.closeTaskItem(id);
		if(result==0) {
			throw new ServiceException("SERVICE.close_failed", HttpStatus.BAD_REQUEST);
		}
		return result;
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

	@Override
	public List<Task> getTasks(String searchText){
		List<Task> results=taskDao.getTasks(searchText);
		if(results.isEmpty()) {
			throw new ServiceException("SERVICE.no_result_found",HttpStatus.NOT_FOUND);
		}
		return results;
	}
	
}
