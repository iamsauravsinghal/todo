package com.saurav.task.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.saurav.task.dao.TaskDao;
import com.saurav.task.exception.ServiceException;
import com.saurav.task.model.Task;


@SpringBootTest
@Transactional
@Rollback(true)
class TaskServiceTest {
	@Mock
	TaskDao taskDao;
	
	@InjectMocks
	TaskServiceImpl taskService;
	
	@Test
	void resultFound() throws Exception{
		String searchText="A";
		List<Task> tasks = new ArrayList<>();
		Task task =new Task();
		tasks.add(task);
		when(this.taskDao.getTasks(anyString())).thenReturn(tasks);
		Assertions.assertFalse(taskService.getTasks(searchText).isEmpty());
	}
	
	@Test
	void resultNotFound() throws Exception{
		String searchText= "Manage";
		when(this.taskDao.getTasks(anyString())).thenReturn(new ArrayList<Task>());
		Assertions.assertThrows(ServiceException.class,()->this.taskService.getTasks(searchText));
	}
	
	@Test
	void validAddTask() throws Exception{
		Task task = new Task();
		task.setPriority(2);
		task.setExpectedDate(LocalDate.now());
		when(this.taskDao.addTaskItem(task)).thenReturn(1);
		Assertions.assertTrue(taskService.addTaskItem(task)>0);
	}
	
	@Test
	void invalidPriorityAddTask() throws Exception{
		Task task = new Task();
		task.setPriority(5);
		task.setExpectedDate(LocalDate.now());
		when(this.taskDao.addTaskItem(task)).thenReturn(1);
		Assertions.assertThrows(ServiceException.class, ()->this.taskService.addTaskItem(task));
	}
	
	@Test
	void invalidExpectedDateAddTask() throws Exception{
		Task task = new Task();
		task.setPriority(3);
		task.setExpectedDate(LocalDate.now().minusDays(2));
		when(this.taskDao.addTaskItem(task)).thenReturn(1);
		Assertions.assertThrows(ServiceException.class, ()->this.taskService.addTaskItem(task));
	}
		
	@Test
	void invalidTaskUpdate() throws Exception{
		Task task = new Task();
		task.setId(0);
		task.setPriority(3);
		task.setExpectedDate(LocalDate.now());
		when(this.taskDao.updateItem(task)).thenReturn(null);
		Assertions.assertThrows(ServiceException.class, ()->this.taskService.updateItem(task));
	}
	
	@Test
	void invalidPriorityTaskUpdate() throws Exception{
		Task task = new Task();
		task.setPriority(5);
		task.setExpectedDate(LocalDate.now());
		when(this.taskDao.updateItem(task)).thenReturn(task);	
		Assertions.assertThrows(ServiceException.class, ()->this.taskService.updateItem(task));
	}
	
	@Test
	void invalidExpectedDateTaskUpdate() throws Exception{
		Task task = new Task();
		task.setPriority(3);
		task.setExpectedDate(LocalDate.now().minusDays(2));
		when(this.taskDao.updateItem(task)).thenReturn(task);	
		Assertions.assertThrows(ServiceException.class, ()->this.taskService.updateItem(task));
	}
	
	@Test
	void validTaskUpdate() throws Exception{
		Task task = new Task();
		task.setId(1);
		task.setPriority(2);
		task.setExpectedDate(LocalDate.now());
		when(this.taskDao.updateItem(task)).thenReturn(task);	
		Assertions.assertNotNull(taskService.updateItem(task));
	}
	
	@Test
	void validTaskDelete() throws Exception{
		Integer id=1;
		when(this.taskDao.closeTaskItem(anyInt())).thenReturn(1);
		Assertions.assertEquals(1,taskService.closeTaskItem(id));
	}
	
	@Test
	void invalidTaskDelete() throws Exception{
		Integer id=0;
		when(this.taskDao.closeTaskItem(anyInt())).thenReturn(0);
		Assertions.assertThrows(ServiceException.class,()-> this.taskService.closeTaskItem(id));
	}
}
