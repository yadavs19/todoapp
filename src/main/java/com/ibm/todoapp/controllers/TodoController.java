package com.ibm.todoapp.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.todoapp.exceptions.TodoNotFoundException;
import com.ibm.todoapp.exceptions.UnAuthorizedException;
import com.ibm.todoapp.models.Role;
import com.ibm.todoapp.models.Todo;
import com.ibm.todoapp.models.User;
import com.ibm.todoapp.services.ITodoService;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

	private static final int Todo = 0;
	@Autowired
	ITodoService todoSvc;

	@GetMapping()
	@PreAuthorize("hasAnyRole('User','Admin')")
	public List<Todo> getAllTodos(User user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String role = authentication.getAuthorities().iterator().next().getAuthority();

		List<Todo> todos = new ArrayList<>();

		if (role.equals("ROLE_Admin")) {
			todos = todoSvc.getAllTodos();

		} else if (role.equals("ROLE_User")) {
			String username = authentication.getName();
			todos = todoSvc.getTodoByCreatedBy(username);
		}
		return todos;

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('User','Admin')")
	public Todo getByTodoId(@PathVariable int id) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String role = authentication.getAuthorities().iterator().next().getAuthority();

		Todo todos = null;

		if (role.equals("ROLE_Admin")) {
			todos = todoSvc.getById(id);
			if (todos == null) {
				throw new TodoNotFoundException("Todo Not Found");
			}
		} else if (role.equals("ROLE_User")) {
			String username = authentication.getName();
			todos = todoSvc.getByIdAndCreatedBy(id, username);
			if (todos == null) {
				throw new UnAuthorizedException("Sorry,your request could not be processed");
			}
		}

		return todos;

	}

	@GetMapping("/title/{title}")
	@PreAuthorize("hasAnyRole('User','Admin')")
	public List<Todo> getByTodoTitle(@PathVariable String title) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String role = authentication.getAuthorities().iterator().next().getAuthority();

		List<Todo> todos = null;

		if (role.equals("ROLE_Admin")) {
			todos = todoSvc.getByTitle(title);
			if (todos == null) {
				throw new TodoNotFoundException("Todo Not Found");
			}
		} else if (role.equals("ROLE_User")) {
			String username = authentication.getName();
			todos = todoSvc.getByTitleAndCreatedBy(title, username);
			if (todos.isEmpty()) {
				throw new UnAuthorizedException("Sorry,your request could not be processed");
			}
		}

		return todos;

	}

	@PostMapping()
	@PreAuthorize("hasAnyRole('User','Admin')")
	public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) { // modelbinding ? spring validation
																			// framework
		var newTodo = todoSvc.addTodo(todo);
		return new ResponseEntity<Todo>(newTodo, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('User','Admin')")
	public ResponseEntity<Todo> updateTodo(@PathVariable int id, @Valid @RequestBody Todo updatedTodo) { // modelbinding ?
																									// spring validation
																									// framework
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String role = authentication.getAuthorities().iterator().next().getAuthority();
		
		Todo todos = null;
		if (role.equals("ROLE_Admin")) {
			todos = todoSvc.getById(id);
			if(todos!=null) {
				todos = todoSvc.updateTodo(id, updatedTodo);
				return new ResponseEntity<Todo>(todos, HttpStatus.OK);
			}else {
				throw new UnAuthorizedException("Sorry,your request could not be processed");
			}
			
		}else if(role.equals("ROLE_User")) {
			String username = authentication.getName();
			todos = todoSvc.getByIdAndCreatedBy(id, username);
			if(todos!=null) {
				todos = todoSvc.updateTodo(id, updatedTodo);
				return new ResponseEntity<Todo>(todos, HttpStatus.OK);
			}else{
				throw new UnAuthorizedException("Sorry,your request could not be processed");
			}
		}else {
			throw new UnAuthorizedException("Sorry,your request could not be processed");
		}
		
		
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('User','Admin')")
	public ResponseEntity<Todo> deleteTodo(@PathVariable int id) { // modelbinding ? spring validation framework
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String role = authentication.getAuthorities().iterator().next().getAuthority();
		
		Todo todos = null;
		if (role.equals("ROLE_Admin")) {
			todos = todoSvc.getById(id);
			if(todos!=null) {
				todos = todoSvc.deleteTodo(id);
			}else {
				throw new TodoNotFoundException("Todo Not Found");
			}

		} else if (role.equals("ROLE_User")) {
			String username = authentication.getName();
			todos = todoSvc.getByIdAndCreatedBy(id, username);
			if(todos!=null) {
				todoSvc.deleteByIdAndCreatedBy(id,username);
			}else {
				throw new UnAuthorizedException("Sorry,your request could not be processed");
			}
		}else {
			throw new UnAuthorizedException("Sorry,your request could not be processed");
		}
		return null;
	}

	// MethodArgumentNotValidException
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
