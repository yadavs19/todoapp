package com.ibm.todoapp;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ibm.todoapp.models.Todo;
import com.ibm.todoapp.repository.TodoJPARepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoappApplicationTests {

	@Autowired
	private TodoJPARepository todoJPARepository;
	
	 // JUnit test for CreateTodo
    @Test
    @Order(1)
    @Rollback(value = false)
    public void CreateTodoTest(){

        Todo todo = Todo.builder()
                .title("Java")
                .description("Learn Java")
                .build();

        todoJPARepository.save(todo);

        Assertions.assertThat(todo.getId()).isGreaterThan(0);
    }
    
    @Test
    @Order(2)
    public void getTodoTest(){

    	Todo todo = todoJPARepository.findById(1).get();

        Assertions.assertThat(todo.getId()).isEqualTo(1);

    }
    
    @Test
    @Order(3)
    public void getListOfTodoTest(){

        List<Todo> todo = todoJPARepository.findAll();

        Assertions.assertThat(todo.size()).isGreaterThan(0);

    }
    
    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateTodoTest(){

        Todo todo = todoJPARepository.findById(1).get();

        todo.setTitle("Python");

        Todo todoUpdated =  todoJPARepository.save(todo);

        Assertions.assertThat(todoUpdated.getTitle()).isEqualTo("Python");

    }
    
    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteTodoTest(){

    	Todo todo = todoJPARepository.findById(1).get();

    	todoJPARepository.delete(todo);
    	
    	Optional<Todo> deletedTodo = todoJPARepository.findById(1);

        Assertions.assertThat(deletedTodo).isEmpty();
    }
    
}
