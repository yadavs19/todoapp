package com.ibm.todoapp.services.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.todoapp.models.Todo;
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
	public Todo getById(int id) {
		// TODO Auto-generated method stub
		var todos = todoDbRepo.findById(id);
		return todos.get();
	}

	@Override
	public Todo addTodo(Todo todo) {
		return todoDbRepo.save(todo);
	}

	@Override
	public Todo updateTodo(int id, Todo todo) {
		// TODO Auto-generated method stub
		Optional<Todo> existingTodo = todoDbRepo.findById(id);
		if (existingTodo.isPresent()) {
			Todo updateTodo = existingTodo.get();
			updateTodo.setTitle(todo.getTitle());
			// Set createdBy and createdDate value unchanged
			String createdBy = updateTodo.getCreatedBy();
			Date createdDate = updateTodo.getCreatedDate();
			// Set the createdBy and createdDate value on the updatedTodo
			todo.setCreatedBy(createdBy);
			todo.setCreatedDate(createdDate);
			return todoDbRepo.save(todo);
		}
		return null;

	}

	@Override
	public Todo deleteTodo(int id) {
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
	public Todo getByIdAndCreatedBy(int id, String username) {
		// TODO Auto-generated method stub
		var todos = todoDbRepo.getByIdAndCreatedBy(id,username);
		return todos;
	}

	@Override
	public List<Todo> getByTitleAndCreatedBy(String title, String username) {
		// TODO Auto-generated method stub
		var todos = todoDbRepo.getByTitleAndCreatedBy(title,username);
		return todos;
	}

	@Override
	public void deleteByIdAndCreatedBy(int id, String username) {
		// TODO Auto-generated method stub
		todoDbRepo.deleteByIdAndCreatedBy(id,username);
	}
}
