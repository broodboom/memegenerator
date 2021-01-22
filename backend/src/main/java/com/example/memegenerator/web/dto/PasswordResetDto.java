package com.example.memegenerator.web.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetDto {
	
	@NotNull
	public String token;

	@NotNull
	public String password;
}
