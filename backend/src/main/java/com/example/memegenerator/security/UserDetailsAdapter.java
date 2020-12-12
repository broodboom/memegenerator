package com.example.memegenerator.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

import com.example.memegenerator.domain.User;

public class UserDetailsAdapter implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 8982330570447520717L;

    private User user;

    public UserDetailsAdapter(Optional<User> user) {
        this.user = user.get();
    }

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole().getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isActivated();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActivated();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isActivated();
    }

    @Override
    public boolean isEnabled() {
        return user.isActivated();
    }
}
