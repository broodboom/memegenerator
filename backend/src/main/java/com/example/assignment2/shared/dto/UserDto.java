package com.example.assignment2.shared.dto;

import lombok.Getter;
import lombok.Setter;

public class UserDto {
	
	@Getter
	@Setter
	public int id;
	
	@Getter
	@Setter
	public String username;
	
	@Setter
	@Getter
	public String password;
	
	@Setter
	@Getter
	public String email;
	
	@Setter
	@Getter
	public int activated;
}
