package com.saurav.task.service;

import java.util.List;

import com.saurav.task.model.Task;


public interface TaskService {
	Integer addTaskItem(Task task);
	Integer closeTaskItem(Integer id);
	Task updateItem(Task task);
	List<Task> getTasks(String searchText);
}

