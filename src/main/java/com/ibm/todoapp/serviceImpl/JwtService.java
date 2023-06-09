package com.ibm.todoapp.serviceImpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ibm.todoapp.models.JwtRequest;
import com.ibm.todoapp.models.JwtResponse;
import com.ibm.todoapp.models.Role;
import com.ibm.todoapp.models.User;
import com.ibm.todoapp.repository.UserRepository;
import com.ibm.todoapp.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(userName, userPassword);

        UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

//        User user = userRepo.findByUserName(userName).get(0);
        return new JwtResponse(newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUserName(username);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthority(user)
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        Role role = user.getRole();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
