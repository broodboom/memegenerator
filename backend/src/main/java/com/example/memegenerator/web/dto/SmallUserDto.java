package com.example.memegenerator.web.dto;

import javax.validation.constraints.NotNull;

import com.example.memegenerator.security.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmallUserDto {

	public long id;

	@NotNull
	public String username;

	@NotNull
	public String email;

	public int activated;

	public Role role;
}