package com.ibm.todoapp.models;

import java.util.Date;
import java.util.Objects;

public class Todo {
	private int id;
	private String title;
	private String description;
	private boolean status;
	private Date targetDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}
	public Todo(int id, String title, String description, boolean status, Date targetDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.targetDate = targetDate;
	}
	public Todo() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(description, id, status, targetDate, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		return Objects.equals(description, other.description) && id == other.id && status == other.status
				&& Objects.equals(targetDate, other.targetDate) && Objects.equals(title, other.title);
	}
	@Override
	public String toString() {
		return "Todo [id=" + id + ", title=" + title + ", description=" + description + ", status=" + status
				+ ", targetDate=" + targetDate + "]";
	} 
}
