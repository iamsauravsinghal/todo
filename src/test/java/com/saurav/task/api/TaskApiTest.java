package com.saurav.task.api;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saurav.task.model.Task;
import com.saurav.task.service.TaskService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskApi.class)
class TaskApiTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private TaskService taskService;
	
	@Test
	public void addTaskItem() throws Exception{
		Task task=new Task();
		task.setExpectedDate(LocalDate.now());
		task.setPriority(2);
		task.setStatus("open");
		task.setTaskName("Test Task");
		Mockito.when(this.taskService.addTaskItem(task)).thenReturn(1);
		this.mvc.perform(MockMvcRequestBuilders
				.post("/task")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(this.mapper.writeValueAsString(task)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("0")));
	}
	
	@Test
	public void updateTaskItem() throws Exception{
		Task task = new Task();
		task.setExpectedDate(LocalDate.now());
		task.setPriority(2);
		task.setId(1);
		task.setTaskName("test");
		task.setStatus("open");
		task.setTaskName("Test Task");
		
		Mockito.when(this.taskService.updateItem(task)).thenReturn(task);
		this.mvc.perform(MockMvcRequestBuilders
				.put("/task")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(this.mapper.writeValueAsString(task)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void closeTaskItem() throws Exception{		
//		when(this.taskService.updateItem(task)).thenReturn(task);
		this.mvc.perform(MockMvcRequestBuilders
				.delete("/task/1")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk());
//		.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("0")));
	}
	
	@Test
	public void getTasks() throws Exception{
		Task task = new Task();
		task.setExpectedDate(LocalDate.now());
		task.setPriority(2);
		task.setId(1);
		task.setTaskName("test");
		task.setStatus("open");
		task.setTaskName("Test Task");
		
		List<Task> tasks = new LinkedList<Task>();
		tasks.add(task);
		Mockito.when(this.taskService.getTasks("ask")).thenReturn(tasks);
		this.mvc.perform(MockMvcRequestBuilders
				.get("/task/ask")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk());
//		.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("0")));
	}
	
	
}
