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

import com.ibm.todoapp.dto.UserDTO;
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
	public List<UserDTO> getAllUser() throws UserNotFoundException{
		List<User> listUsers = userService.getAllUser();
		List<UserDTO> listUserDTO = userService.UsertoUserDTO(listUsers);
		if(listUserDTO.size()==0)
			throw new UserNotFoundException("User Not Found");
			return listUserDTO;
		}
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('Admin')")
	public UserDTO getByUserId(@PathVariable Integer id) throws UserNotFoundException{
		User users = userService.getById(id);
		UserDTO userDTO = userService.UsertoUserDTO(users);
		if(userDTO == null)
			throw new UserNotFoundException("User Not Found");
		else
			return userDTO;
	}
	
	@PostMapping()
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {	// modelbinding ? spring validation framework 
		User newUser = userService.UserDTOtoUser(userDTO);
		User newUser1 = userService.addUser(newUser);
		UserDTO userDTO1 = userService.UsertoUserDTO(newUser1);
		return new ResponseEntity<UserDTO>(userDTO1, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDTO userDTO) throws UserNotFoundException {	// modelbinding ? spring validation framework 
		User newUser = userService.UserDTOtoUser(userDTO);
		User newUser1 = userService.updateUser(id, newUser);
		UserDTO userDTO1 = userService.UsertoUserDTO(newUser1);
		
		if(userDTO1 != null)
			return new ResponseEntity<UserDTO>(userDTO1, HttpStatus.OK);
		else
			//return (ResponseEntity<com.ibm.todoapp.models.Todo>) ResponseEntity.notFound();
			throw new UserNotFoundException("User Not Found");
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<User> deleteUser(@PathVariable Integer id) throws UserNotFoundException {	// modelbinding ? spring validation framework 
		User user = userService.deleteUser(id);
		if(user != null)
			return null;
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
