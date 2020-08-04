package com.saurav.task.dao;

import java.util.List;

import com.saurav.task.model.Task;

public interface TaskDao{
	Integer addTaskItem(Task task);
	Integer closeTaskItem(Integer id);
	Task updateItem(Task task);
	List<Task> getTasks(String searchText);
}
