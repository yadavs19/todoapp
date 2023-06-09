package com.ibm.todoapp.controllers;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public List<UserDTO> getAllUser() throws UserNotFoundException {
		List<User> listUsers = userService.getAllUser();
		List<UserDTO> listUserDTO = userService.UsertoUserDTO(listUsers);
		if (listUserDTO.size() == 0)
			throw new UserNotFoundException("User Not Found");
		return listUserDTO;
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('Admin')")
	public UserDTO getByUserId(@PathVariable Integer id) throws UserNotFoundException {
		Optional<User> users = userService.getById(id);
		if (!users.isEmpty()) {
			UserDTO userDTO = userService.UsertoUserDTO(users);
			return userDTO;
		} else {
			throw new UserNotFoundException("User Not Found");
		}
	}

	@PostMapping()
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) { // modelbinding ? spring validation
																						// framework
		User newUser = userService.UserDTOtoUser(userDTO);
		User newUser1 = userService.addUser(newUser);
		UserDTO userDTO1 = userService.UsertoUserDTO(newUser1);
		return new ResponseEntity<UserDTO>(userDTO1, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDTO userDTO)
			throws UserNotFoundException { // modelbinding ? spring validation framework
		Optional<User> users = userService.getById(id);
		if (!users.isEmpty()) {
			User newUser = userService.UserDTOtoUser(userDTO);
			User newUser1 = userService.updateUser(id, newUser);
			UserDTO userDTO1 = userService.UsertoUserDTO(newUser1);
			return new ResponseEntity<UserDTO>(userDTO1, HttpStatus.OK);
		} else {
			throw new UserNotFoundException("User Not Found");
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<String> deleteUser(@PathVariable Integer id) throws UserNotFoundException { // modelbinding ?
																										// spring
																										// validation
																										// framework
		Optional<User> users = userService.getById(id);
		if (!users.isEmpty()) {
			User user = userService.deleteUser(id);
			return ResponseEntity.ok("User deleted successfully!!!");
		} else {
			throw new UserNotFoundException("User Not Found");
		}
	}

}
