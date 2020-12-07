package com.example.memegenerator.request;

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
