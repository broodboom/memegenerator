package com.example.memegenerator.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetDto {
	
	public String token;

	public String password;
}
