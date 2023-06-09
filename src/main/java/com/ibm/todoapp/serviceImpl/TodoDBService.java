package com.ibm.todoapp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.todoapp.dto.TodoDTO;
import com.ibm.todoapp.dto.UserDTO;
import com.ibm.todoapp.exceptions.TodoNotFoundException;
import com.ibm.todoapp.models.Todo;
import com.ibm.todoapp.models.User;
import com.ibm.todoapp.repository.TodoJPARepository;
import com.ibm.todoapp.services.ITodoService;

@Service
@Transactional
public class TodoDBService implements ITodoService {
	// h2

	@Autowired
	private TodoJPARepository todoDbRepo;

	@Override
	public List<Todo> getAllTodos() {
		// TODO Auto-generated method stub
		var todos = todoDbRepo.findAll();
		return todos;
	}

	@Override
	public Optional<Todo> getById(Integer id) {
		// TODO Auto-generated method stub
		Optional<Todo> todos = todoDbRepo.findById(id);
		return todos;
	}

	@Override
	public Todo addTodo(Todo todo) {
		return todoDbRepo.save(todo);
	}

	@Override
	public Todo updateTodo(Integer id, Todo todo) {
		// TODO Auto-generated method stub
		Optional<Todo> existingTodo = todoDbRepo.findById(id);
		if (existingTodo.isPresent()) {
			Todo updateTodo = existingTodo.get();
			updateTodo.setTitle(todo.getTitle());
			updateTodo.setDescription(todo.getDescription());
			// Set createdBy and createdDate value unchanged
			String createdBy = updateTodo.getCreatedBy();
			Date createdDate = updateTodo.getCreatedDate();
			// Set the createdBy and createdDate value on the updatedTodo
			todo.setCreatedBy(createdBy);
			todo.setCreatedDate(createdDate);
			return todoDbRepo.save(updateTodo);
		}
		return null;

	}

	@Override
	public Todo deleteTodo(Integer id) {
		// TODO Auto-generated method stub
		Optional<Todo> existingTodo = todoDbRepo.findById(id);
		if (existingTodo.isPresent()) {
			todoDbRepo.deleteById(id);
			return existingTodo.get();
		}
		return null;
	}

	@Override
	public List<Todo> getByTitle(String title) {
		// TODO Auto-generated method stub
		var todos = todoDbRepo.findByTitle(title);
		return todos;
	}

	@Override
	public List<Todo> getTodoByCreatedBy(String username) {
		// TODO Auto-generated method stub
		var todos = todoDbRepo.getTodoByCreatedBy(username);
		return todos;
	}

	@Override
	public Todo getByIdAndCreatedBy(Integer id, String username) {
		// TODO Auto-generated method stub
		var todos = todoDbRepo.getByIdAndCreatedBy(id, username);
		return todos;
	}

	@Override
	public List<Todo> getByTitleAndCreatedBy(String title, String username) {
		// TODO Auto-generated method stub
		var todos = todoDbRepo.getByTitleAndCreatedBy(title, username);
		return todos;
	}

	@Override
	public void deleteByIdAndCreatedBy(Integer id, String username) {
		// TODO Auto-generated method stub
		todoDbRepo.deleteByIdAndCreatedBy(id, username);
	}

	@Override
	public TodoDTO TodotoTodoDTO(Todo todo) {
		TodoDTO todoDTO = new TodoDTO();
		todoDTO.setId(todo.getId());
		todoDTO.setTitle(todo.getTitle());
		todoDTO.setDescription(todo.getDescription());
		return todoDTO;
	}

	@Override
	public List<TodoDTO> TodotoTodoDTO(List<Todo> listTodos) {
		// TODO Auto-generated method stub
		List<TodoDTO> listTodoDTOs = new ArrayList<>();
		for (Todo todo : listTodos) {
			TodoDTO todoDTO = TodotoTodoDTO(todo);
			listTodoDTOs.add(todoDTO);
		}
		return listTodoDTOs;
	}

	@Override
	public Todo TodoDTOtoTodo(TodoDTO todoDTO) {
		Todo todo = new Todo();
		todo.setId(todoDTO.getId());
		todo.setTitle(todoDTO.getTitle());
		todo.setDescription(todoDTO.getDescription());
		return todo;
	}

	@Override
	public List<Todo> TodoDTOtoTodo(List<TodoDTO> lisTodoDTO) {
		List<Todo> listTodo = new ArrayList<>();
		for (TodoDTO todoDTO : lisTodoDTO) {
			Todo todo = TodoDTOtoTodo(todoDTO);
			listTodo.add(todo);
		}
		return listTodo;
	}

	@Override
	public TodoDTO TodotoTodoDTO(Optional<Todo> todos) {
		// TODO Auto-generated method stub
			Todo todo = todos.get();
	        TodoDTO todoDTO = new TodoDTO();
	        todoDTO.setId(todo.getId());
	        todoDTO.setDescription(todo.getDescription());
	        todoDTO.setTitle(todo.getTitle());
	        return todoDTO;
	}
}
