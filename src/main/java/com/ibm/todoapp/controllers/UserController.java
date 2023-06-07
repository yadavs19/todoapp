package com.ibm.todoapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.ibm.todoapp.exceptions.UserNotFoundException;
import com.ibm.todoapp.models.User;
import com.ibm.todoapp.services.IUserService;

@RestController
@RequestMapping(("/user"))
public class UserController {

    @Autowired
    private IUserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @GetMapping()
    @PreAuthorize("hasRole('Admin')")
	public List<User> getAllUser() throws UserNotFoundException{
		 
		var users = userService.getAllUser();
		if(users.size()==0)
			throw new UserNotFoundException("User Not Found");
			return users;
		}
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('Admin')")
	public User getByUserId(@PathVariable int id) throws UserNotFoundException{
		var users = userService.getById(id);
		if(users == null)
			throw new UserNotFoundException("User Not Found");
		else
			return users;
	}
	
	@PostMapping()
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {	// modelbinding ? spring validation framework 
		
		var newUser = userService.addUser(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<User> updateUser(@PathVariable int id, @Valid @RequestBody User user) throws UserNotFoundException {	// modelbinding ? spring validation framework 
		var newUser = userService.updateUser(id, user);
		
		if(newUser != null)
			return new ResponseEntity<User>(newUser, HttpStatus.OK);
		else
			//return (ResponseEntity<com.ibm.todoapp.models.Todo>) ResponseEntity.notFound();
			throw new UserNotFoundException("User Not Found");
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<User> deleteUser(@PathVariable int id) throws UserNotFoundException {	// modelbinding ? spring validation framework 
		var user = userService.deleteUser(id);
		if(user != null)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		else
			throw new UserNotFoundException("User Not Found");
	}
	
	// MethodArgumentNotValidException
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String> handleValidationExceptions(MethodArgumentNotValidException ex){
		Map<String,String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
