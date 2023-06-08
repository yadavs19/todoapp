package com.ibm.todoapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String getEncodedPassword(String password) {
		String encodePassword = passwordEncoder.encode(password);
		return encodePassword;
	}
}
