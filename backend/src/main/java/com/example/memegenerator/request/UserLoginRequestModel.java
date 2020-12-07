package com.example.memegenerator.request;
import lombok.Getter;
import lombok.Setter;

public class UserLoginRequestModel {

	@Getter
	@Setter
	public String username;
	
	@Getter
	@Setter
	public String password;
}
