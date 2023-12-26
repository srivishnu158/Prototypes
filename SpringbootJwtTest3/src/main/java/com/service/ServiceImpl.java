package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.entity.Role;
import com.entity.User;
import com.config.*;
import com.model.LoginRequestModel;
import com.model.RegisterModel;
import com.model.ResponseModel;
import com.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceImpl {
	private AuthenticationManager authenticationManager;
	private JwtService jwtService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    public ServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

	

	public User registerRequest(RegisterModel registerModel) {
		User user = new User();
		user.setName(registerModel.getName());
		user.setEmail(registerModel.getEmail());
		user.setPassword(passwordEncoder.encode(registerModel.getPassword()));
		user.setRole(Role.User);
		return userRepository.save(user);
	}
	
	
	
public ResponseModel loginRequestHandler(LoginRequestModel loginRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
				(loginRequest.getEmail(),loginRequest.getPassword()));
		var user = userRepository.getByEmail(loginRequest.getEmail());
		var token = jwtService.generateToken(user);
		return ResponseModel.builder().token(token).build();
	}

public UserDetails getUserDetails(String useremail) {
	return userRepository.getUserByEmail(useremail);
}
}
