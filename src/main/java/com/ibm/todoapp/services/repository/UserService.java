package com.ibm.todoapp.services.repository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibm.todoapp.models.Role;
import com.ibm.todoapp.models.User;
import com.ibm.todoapp.repository.RoleRepository;
import com.ibm.todoapp.repository.UserRepository;
import com.ibm.todoapp.services.IUserService;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private RoleRepository rolerepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void initRoleAndUser() {

		Role adminRole = new Role();
		adminRole.setId(1);
		adminRole.setRoleName("Admin");
		adminRole.setRoleDescription("Admin role");
		rolerepo.save(adminRole);

		Role userRole = new Role();
		userRole.setId(2);
		userRole.setRoleName("User");
		userRole.setRoleDescription("Default role for newly created record");
		rolerepo.save(userRole);

		User adminUser = new User();
		adminUser.setId(1);
		adminUser.setUserName("admin");
		adminUser.setUserPassword(getEncodedPassword("admin@123"));
		adminUser.setUserFirstName("admin");
		adminUser.setUserLastName(" ");
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);
		userrepo.save(adminUser);

		User user = new User();
		user.setId(2);
		user.setUserName("user1");
		user.setUserPassword(getEncodedPassword("user@123"));
		user.setUserFirstName("user");
		user.setUserLastName("1 ");
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(userRole);
		user.setRole(userRoles);
		userrepo.save(user);
	}

//	public User registerNewUser(User user) {
//		Role role = rolerepo.findById("User").get();
//		Set<Role> userRoles = new HashSet<>();
//		userRoles.add(role);
//		user.setRole(userRoles);
//		user.setUserPassword(getEncodedPassword(user.getUserPassword()));
//
//		return userrepo.save(user);
//	}

	@Override
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public List<User> getAllUser() {
		return (List<User>) userrepo.findAll();

	}

	@Override
	public User getById(int id) {
		// TODO Auto-generated method stub
		Optional<User> optionalUser = userrepo.findById(id);
		return optionalUser.get();
	}

	@Override
	public User addUser(User user) {
		Role role = rolerepo.findById(2).get();
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(role);
		user.setRole(userRoles);
		user.setUserPassword(getEncodedPassword(user.getUserPassword()));

		return userrepo.save(user);
	}

	@Override
	public User updateUser(int id, User user) {
		// TODO Auto-generated method stub
		Optional<User> existingUser = userrepo.findById(id);
		if(existingUser.isPresent()) {
			User updateUser = existingUser.get();
			updateUser.setUserName(user.getUserName());
			updateUser.setUserFirstName(user.getUserFirstName());
			updateUser.setUserLastName(user.getUserLastName());
			updateUser.setUserPassword(getEncodedPassword(user.getUserPassword()));
			Set<Role> role = updateUser.getRole();
			user.setRole(role);
			return userrepo.save(user);
		}
		return null;
	}

	@Override
	public User deleteUser(int id) {
		// TODO Auto-generated method stub
		Optional<User> existingUser = userrepo.findById(id);
		if(existingUser.isPresent()) {
			userrepo.deleteById(id);
			return existingUser.get();
		}
		return null;
	}
}
