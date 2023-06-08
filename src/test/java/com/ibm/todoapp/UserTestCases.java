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

import com.ibm.todoapp.models.User;
import com.ibm.todoapp.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserTestCases {

	@Autowired
	private UserRepository userRepository;
	
	 // JUnit test for CreateTodo
    @Test
    @Order(1)
    @Rollback(value = false)
    public void CreateUserTest(){

        User user = User.builder()
        		.userName("user5")
        		.userFirstName("User")
        		.userLastName("5 ")
        		.userPassword("user@123")
        		.build();
        
        userRepository.save(user);
        
        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }
    
	@Test
	@Order(2)
	public void getUserTest() {

		User user = userRepository.findById(1).get();

		Assertions.assertThat(user.getId()).isEqualTo(1);

	}

  @Test
  @Order(3)
  public void getListOfTUserTest(){

      List<User> user = userRepository.findAll();

      Assertions.assertThat(user.size()).isGreaterThan(0);

  }
  
	@Test
	@Order(4)
	@Rollback(value = false)
	public void updateUserTest() {

		User user = userRepository.findById(3).get();

		user.setUserName("user6");

		User userUpdated = userRepository.save(user);

		Assertions.assertThat(userUpdated.getUserName()).isEqualTo("user6");

	}
	
	    @Test
		@Order(5)
		@Rollback(value = false)
		public void deleteUserTest() {

			User user = userRepository.findById(3).get();

			userRepository.delete(user);

			Optional<User> deletedUser = userRepository.findById(3);

			Assertions.assertThat(deletedUser).isEmpty();
		}

	
}
