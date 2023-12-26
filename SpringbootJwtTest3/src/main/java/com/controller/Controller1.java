package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.User;
import com.model.LoginRequestModel;
import com.model.RegisterModel;
import com.model.ResponseModel;
import com.service.ServiceImpl;

@RestController
public class Controller1 {
	@Autowired
	ServiceImpl serviceImpl;
	@GetMapping("/validate")
	public String validatormap() {
		return "hell";
	}
	
	@PostMapping("/register")
	public User registerRequest(@RequestBody RegisterModel registerModel) {
		return serviceImpl.registerRequest(registerModel);
	}
	
	@PostMapping("/login")
	public ResponseModel loginRequest(@RequestBody LoginRequestModel loginModel) {
		return serviceImpl.loginRequestHandler(loginModel);
	}
}
