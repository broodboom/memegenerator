package com.example.memegenerator.shared.dto;

import lombok.Getter;
import lombok.Setter;

public class UserDto {
	
	@Getter
	@Setter
	public int id;
	
	@Getter
	@Setter
	public String username;
	
	//@Getter
	//@Setter
	//public Set<Achievement> achievements = new HashSet<>();
	
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
