package com.saurav.task.dao;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.saurav.task.model.Task;

@SpringBootTest
@Transactional
@Rollback(true)
class TaskDaoTest {
	
	@Autowired
	private TaskDao taskDao;
	
	@Test
	void invalidTaskUpdate() throws Exception{
		Task task = new Task();
		task.setId(-1);
		task.setPriority(2);
		task.setTaskName("Test Task");
		task.setExpectedDate(LocalDate.now());
		Assertions.assertNull(taskDao.updateItem(task));
	}
	
	@Test
	void validTaskUpdate() throws Exception{
		Task task = new Task();
		task.setId(1);
		task.setPriority(2);
		task.setTaskName("Test Task");
		task.setExpectedDate(LocalDate.now());
		Assertions.assertNotNull(taskDao.updateItem(task));
	}
	
	@Test
	void validAddTask() throws Exception{
		Task task = new Task();
		task.setPriority(8);
		task.setExpectedDate(LocalDate.now());
		task.setTaskName("Test Task");
		Assertions.assertTrue(taskDao.addTaskItem(task)>0);
	}
	
	@Test
	void validTaskDelete() throws Exception{
		Integer id = 1;
		Assertions.assertEquals(1,taskDao.closeTaskItem(id));
	}
	
	@Test
	void invalidTaskDelete() throws Exception{
		Integer id = 0;
		Assertions.assertEquals(0,taskDao.closeTaskItem(id));
	}
	
	@Test
	void resultFound() throws Exception{
		String searchText= "A";
		Assertions.assertFalse(taskDao.getTasks(searchText).isEmpty());
	}
	
	@Test
	void resultNotFound() throws Exception{
		String searchText= "Manage";
		Assertions.assertTrue(taskDao.getTasks(searchText).isEmpty());
	}
	
}
