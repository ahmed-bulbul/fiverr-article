package com.article.util;

import com.article.entity.User;
import com.article.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
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
      user.setPrivileges(userDetails.getPrivileges());
      return user;
    }
    return null;
  }
}
