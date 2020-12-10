package com.example.memegenerator.security;

import com.example.memegenerator.domain.User;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class Guard {

    public boolean checkWriteAccess(final Authentication authentication, final int userId) {
        // final User user = ((UserDetailsAdapter) authentication.getPrincipal()).getUser();

        // final boolean allowed = isUser(user, userId) || isAdmin(user, userId);

        // if(allowed) {
        //     log.info("User {} tried to write to user {} [ALLOWED]", user.getId(), userId);
        // } else {
        //     log.warn("User {} tried to write to user {} [DENIED]", user.getId(), userId);
        // }

        // return allowed;

        return true;
    }

    public boolean checkReadAccess(final Authentication authentication, final int userId) {
        // final User user = ((UserDetailsAdapter) authentication.getPrincipal()).getUser();

        // final boolean allowed = isUser(user, userId);

        // if(allowed) {
        //     log.info("User {} tried to read from user {} [ALLOWED]", user.getId(), userId);
        // } else {
        //     log.warn("User {} tried to read from user {} [DENIED]", user.getId(), userId);
        // }

        // return allowed;

        return true;
    }

    private boolean isAdmin(final User user, final int userId) {
        return user.getRole().equals(Role.Admin) && user.getId() == userId;
    }

    private boolean isUser(final User user, final int userId) {
        return user.getRole().equals(Role.User) && user.getId() == userId;
    }
}