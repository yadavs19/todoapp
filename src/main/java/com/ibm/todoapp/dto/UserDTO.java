package com.ibm.todoapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ibm.todoapp.models.Role;

import lombok.Data;

public class UserDTO {
	
	private Integer Id;
	@NotBlank(message = "username is required")
    private String UserName;
	@Size(min = 5, max = 15, message = "length should be morethan 5 and less than 15 characters")
    private String UserFirstName;
	@Size(min = 1, max = 10, message = "length should be morethan 1 and less than 10 characters")
    private String UserLastName;
    @NotBlank(message = "password field cannot be blank")
    private String UserPassword;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserFirstName() {
		return UserFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		UserFirstName = userFirstName;
	}
	public String getUserLastName() {
		return UserLastName;
	}
	public void setUserLastName(String userLastName) {
		UserLastName = userLastName;
	}
	public String getUserPassword() {
		return UserPassword;
	}
	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}
	
}
