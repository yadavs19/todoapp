package com.ibm.todoapp.services.repository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibm.todoapp.models.Role;
import com.ibm.todoapp.models.User;
import com.ibm.todoapp.repository.RoleRepository;
import com.ibm.todoapp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userrepo;

    @Autowired
    private RoleRepository rolerepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        rolerepo.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        rolerepo.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("admin");
        adminUser.setUserPassword(getEncodedPassword("admin@123"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName(" ");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userrepo.save(adminUser);

       User user = new User();
       user.setUserName("user1");
       user.setUserPassword(getEncodedPassword("user@123"));
       user.setUserFirstName("user");
       user.setUserLastName("1 ");
       Set<Role> userRoles = new HashSet<>();
       userRoles.add(userRole);
       user.setRole(userRoles);
       userrepo.save(user);
    }

    public User registerNewUser(User user) {
        Role role = rolerepo.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userrepo.save(user);
    }
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
