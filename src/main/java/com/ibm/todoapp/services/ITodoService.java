package com.ibm.todoapp.services;

import java.util.List;
import java.util.Optional;

import com.ibm.todoapp.dto.TodoDTO;
import com.ibm.todoapp.models.Todo;

public interface ITodoService {

	
	public List<Todo> getAllTodos();
	
	public Optional<Todo> getById(Integer id);
	
	public List<Todo> getByTitle(String title);
	
	public Todo addTodo(Todo todo);
	
	public Todo updateTodo(Integer id, Todo todo);
	
	public Todo deleteTodo(Integer id);

	public List<Todo> getTodoByCreatedBy(String username);

	public Todo getByIdAndCreatedBy(Integer id, String username);

	public List<Todo> getByTitleAndCreatedBy(String title, String username);

	public void deleteByIdAndCreatedBy(Integer id, String username);

	public List<Todo> TodoDTOtoTodo(List<TodoDTO> lisTodoDTO);

	public Todo TodoDTOtoTodo(TodoDTO todoDTO);

	public List<TodoDTO> TodotoTodoDTO(List<Todo> listTodos);

	public TodoDTO TodotoTodoDTO(Todo todo);

	public TodoDTO TodotoTodoDTO(Optional<Todo> todos);
	
}
