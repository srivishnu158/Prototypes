package com.model;

import lombok.Data;

@Data
public class RegisterModel {
	private String name;
	private String email;
	private String password;
	private String role;
}
