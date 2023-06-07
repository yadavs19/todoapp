package com.ibm.todoapp.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibm.todoapp.models.Todo;

// db integration

@Repository
public interface TodoJPARepository extends JpaRepository<Todo, Integer> {

	public List<Todo> findByTitle(String title);
	
	@Query(value= "select title from Todo t where t.id=1")
	public Collection<Todo> findAllTodos();

	public List<Todo> getTodoByCreatedBy(String username);

	public Todo getByIdAndCreatedBy(int id, String username);

	public List<Todo> getByTitleAndCreatedBy(String title, String username);

	@Modifying
	@Query("DELETE FROM Todo t WHERE t.id = :id AND t.createdBy = :createdBy")
	public void deleteByIdAndCreatedBy(@Param("id") int id, @Param("createdBy") String createdBy);
}
