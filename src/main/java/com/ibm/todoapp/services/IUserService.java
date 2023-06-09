package com.ibm.todoapp.services;

import java.util.List;
import java.util.Optional;

import com.ibm.todoapp.dto.UserDTO;
import com.ibm.todoapp.models.User;

public interface IUserService {

	public List<User> getAllUser();
	
	public Optional<User> getById(Integer id);
		
	public User addUser(User user);
	
	public User updateUser(Integer id, User user);
	
	public User deleteUser(Integer id);

	public void initRoleAndUser();
	
	public UserDTO UsertoUserDTO(User user);

	public List<UserDTO> UsertoUserDTO(List<User> listUsers);
	
	public User UserDTOtoUser(UserDTO userDTO);

	public List<User> UserDTOtoUser(List<UserDTO> listUserDTO);

	public UserDTO UsertoUserDTO(Optional<User> users);
	
}
