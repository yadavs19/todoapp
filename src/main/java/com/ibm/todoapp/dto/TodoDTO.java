package com.ibm.todoapp.dto;

import javax.validation.constraints.Size;

public class TodoDTO {
	
	private Integer Id;
	private String Title;
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
