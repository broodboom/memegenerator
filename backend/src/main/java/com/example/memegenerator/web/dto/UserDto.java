package com.example.memegenerator.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	public long id;

	public String username;

	public String password;

	public String email;

	public int activated;
}