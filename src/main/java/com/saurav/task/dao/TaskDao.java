package com.saurav.task.dao;

import java.util.List;

import com.saurav.task.model.Task;

public interface TaskDao{
	Integer addTaskItem(Task task);
	void closeTaskItem(Integer id);
	Integer deleteTaskItem(Integer id);
	Task updateItem(Task task);
	public Task getTaskById(Integer id);
	List<Integer> getTasks(String searchText);
}
