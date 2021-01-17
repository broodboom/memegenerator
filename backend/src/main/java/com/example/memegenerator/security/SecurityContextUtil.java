package com.example.memegenerator.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {

    private SecurityContextUtil() {
    }

    public static UserDetailsAdapter getSecurityContextUser() {
        
        return (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}