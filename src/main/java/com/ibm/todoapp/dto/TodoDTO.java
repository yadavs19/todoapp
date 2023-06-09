package com.ibm.todoapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TodoDTO {
	
	private Integer Id;
	
	@NotBlank(message = "Title is required.")
	private String Title;
	@Size(min = 5, max = 20, message = "length should be morethan 5 and less than 20 characters")
	private String Description;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
}
