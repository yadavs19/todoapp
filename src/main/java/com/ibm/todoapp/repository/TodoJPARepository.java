package com.ibm.todoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibm.todoapp.models.Todo;

// db integration

@Repository
public interface TodoJPARepository extends JpaRepository<Todo, Integer> {

	List<Todo> findByTitle(String title);
}
