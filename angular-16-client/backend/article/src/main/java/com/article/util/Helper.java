package com.article.util;

import com.article.entity.User;
import com.article.security.CustomUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Helper {
    private static CustomUserDetails loggedInUser;

    public static User getCurrentUser() {
        User user = new User();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getPrincipal() instanceof String) {
                if (loggedInUser == null || !authentication.getPrincipal().equals(loggedInUser.getUsername())) {
                    assert loggedInUser != null;
                    user.setId(loggedInUser.getId());
                    return user;
                }
            }
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            user.setId(userDetails.getId());
            user.setUsername(userDetails.getUsername());
            return user;
        }
        return null;
    }
}
