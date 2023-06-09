package com.ibm.todoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.todoapp.models.Role;
import com.ibm.todoapp.serviceImpl.RoleService;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping({"/role"})
    public Role createNewRole(@RequestBody Role role) {
        return roleService.createNewRole(role);
    }
}
