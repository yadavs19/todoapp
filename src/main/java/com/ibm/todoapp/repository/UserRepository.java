package com.ibm.todoapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.todoapp.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	User getUserByUserName(String userName);

	List<User> findByUserName(String userName);
}
