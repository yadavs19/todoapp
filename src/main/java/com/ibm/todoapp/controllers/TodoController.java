package com.ibm.todoapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.todoapp.models.Todo;
import com.ibm.todoapp.services.repository.TodoStaticService;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
	
	@Autowired
	TodoStaticService todoSvc;
	
	@GetMapping()
	public List<Todo> getAllTodos(){
		
		return todoSvc.getAllTodos();
	}
	
	@GetMapping("/{id}")
	public Todo getByTodoId(@PathVariable int id){
		
		return todoSvc.getById(id);
	}
}
