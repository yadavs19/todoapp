package com.ibm.todoapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.todoapp.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
