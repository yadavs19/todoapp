package com.ibm.todoapp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibm.todoapp.dto.TodoDTO;
import com.ibm.todoapp.dto.UserDTO;
import com.ibm.todoapp.models.Role;
import com.ibm.todoapp.models.Todo;
import com.ibm.todoapp.models.User;
import com.ibm.todoapp.repository.RoleRepository;
import com.ibm.todoapp.repository.UserRepository;
import com.ibm.todoapp.services.IUserService;
import com.ibm.todoapp.util.PasswordEncoderUtil;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private RoleRepository rolerepo;
	
	@Autowired
	private PasswordEncoderUtil encode;

	@Override
	public void initRoleAndUser() {

		Role adminRole = new Role();
		adminRole.setId(1);
		adminRole.setName("Admin");
		adminRole.setDescription("Admin role");
		rolerepo.save(adminRole);

		Role userRole = new Role();
		userRole.setId(2);
		userRole.setName("User");
		userRole.setDescription("Default role for newly created record");
		rolerepo.save(userRole);

		User adminUser = new User();
		adminUser.setId(1);
		adminUser.setUserName("admin");
		adminUser.setUserPassword(encode.getEncodedPassword("admin@123"));
		adminUser.setUserFirstName("admin");
		adminUser.setUserLastName(" ");
		Role adminRoles1 = rolerepo.findByName("Admin");
		adminUser.setRole(adminRoles1);
		userrepo.save(adminUser);

		User user = new User();
		user.setId(2);
		user.setUserName("user1");
		user.setUserPassword(encode.getEncodedPassword("user@123"));
		user.setUserFirstName("user");
		user.setUserLastName("1 ");
		Role userRole1 = rolerepo.findByName("User");
		user.setRole(userRole1);
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
	public List<User> getAllUser() {
		return (List<User>) userrepo.findAll();

	}

	@Override
	public Optional<User> getById(Integer id) {
		// TODO Auto-generated method stub
		Optional<User> optionalUser = userrepo.findById(id);
		return optionalUser;
	}

	@Override
	public User addUser(User user) {
		Role userRole = rolerepo.findByName("User");
		user.setRole(userRole);
		user.setUserPassword(encode.getEncodedPassword(user.getUserPassword()));
		return userrepo.save(user);
	}

	@Override
	public User updateUser(Integer id, User user) {
		// TODO Auto-generated method stub
		Optional<User> existingUser = userrepo.findById(id);
		if(existingUser.isPresent()) {
			User updateUser = existingUser.get();
			updateUser.setUserName(user.getUserName());
			updateUser.setUserFirstName(user.getUserFirstName());
			updateUser.setUserLastName(user.getUserLastName());
			String encodePassword = encode.getEncodedPassword(user.getUserPassword());
			updateUser.setUserPassword(encodePassword);
//			Role role = updateUser.getRole();
//			updateUser.setRole(role);
			return userrepo.save(updateUser);
		}
		return null;
	}

	@Override
	public User deleteUser(Integer id) {
		// TODO Auto-generated method stub
		Optional<User> existingUser = userrepo.findById(id);
		if(existingUser.isPresent()) {
			userrepo.deleteById(id);
			return existingUser.get();
		}
		return null;
	}
	
	@Override
	public UserDTO UsertoUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUserName(user.getUserName());
		userDTO.setUserFirstName(user.getUserFirstName());
		userDTO.setUserLastName(user.getUserLastName());
		userDTO.setUserPassword(user.getUserPassword());
		return userDTO;
	}

	@Override
	public List<UserDTO> UsertoUserDTO(List<User> listUsers) {
		// TODO Auto-generated method stub
		List<UserDTO> listUserDTO = new ArrayList<>();
		for(User user : listUsers) {
			UserDTO userDTO = UsertoUserDTO(user);
			listUserDTO.add(userDTO);
		}
		return listUserDTO;
	}
	
	@Override
	public User UserDTOtoUser(UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setUserName(userDTO.getUserName());
		user.setUserFirstName(userDTO.getUserFirstName());
		user.setUserLastName(userDTO.getUserLastName());
		user.setUserPassword(userDTO.getUserPassword());
		return user;
	}
	
	@Override
	public List<User> UserDTOtoUser(List<UserDTO> listUserDTO){
		List<User> listUser = new ArrayList<>();
		for(UserDTO userDTO : listUserDTO) {
			User user = UserDTOtoUser(userDTO);
			listUser.add(user);
		}
		return listUser;
	}

	@Override
	public UserDTO UsertoUserDTO(Optional<User> users) {
		// TODO Auto-generated method stub
		User user = users.get();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setUserFirstName(user.getUserFirstName());
        userDTO.setUserLastName(user.getUserLastName());
        userDTO.setUserPassword(user.getUserPassword());
        return userDTO;
	}
}
