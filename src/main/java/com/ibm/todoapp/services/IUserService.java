package com.ibm.todoapp.services;

import java.util.List;

import com.ibm.todoapp.models.Todo;
import com.ibm.todoapp.models.User;

public interface IUserService {

	public List<User> getAllUser();
	
	public User getById(int id);
		
	public User addUser(User user);
	
	public User updateUser(int id, User user);
	
	public User deleteUser(int id);

	public void initRoleAndUser();
	
	public String getEncodedPassword(String password);
	
}
