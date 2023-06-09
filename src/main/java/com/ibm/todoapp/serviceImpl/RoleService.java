package com.ibm.todoapp.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.todoapp.models.Role;
import com.ibm.todoapp.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repo;

    public Role createNewRole(Role role) {
        return repo.save(role);
    }
}
