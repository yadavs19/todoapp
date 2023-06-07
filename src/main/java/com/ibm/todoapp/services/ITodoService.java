package com.ibm.todoapp.services;

import java.util.List;

import com.ibm.todoapp.models.Todo;

public interface ITodoService {

	
	public List<Todo> getAllTodos();
	
	public Todo getById(int id);
	
	public List<Todo> getByTitle(String title);
	
	public Todo addTodo(Todo todo);
	
	public Todo updateTodo(int id, Todo todo);
	
	public Todo deleteTodo(int id);

	public List<Todo> getTodoByCreatedBy(String username);

	public Todo getByIdAndCreatedBy(int id, String username);

	public List<Todo> getByTitleAndCreatedBy(String title, String username);

	public void deleteByIdAndCreatedBy(int id, String username);
	
}
