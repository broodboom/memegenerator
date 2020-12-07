package com.example.memegeneratoror.request;

import lombok.Getter;
import lombok.Setter;

public class UserDetailsRequestModel {
	@Getter
	@Setter
	public String username;
	
	@Getter
	@Setter
	public String password;
	
	@Getter
	@Setter
	public String email;
}
