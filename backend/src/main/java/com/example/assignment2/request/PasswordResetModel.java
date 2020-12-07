package com.example.assignment2.request;

import lombok.Getter;
import lombok.Setter;

public class PasswordResetModel {
	@Getter
	@Setter
	public String token;
	
	@Getter
	@Setter
	public String password;
}
