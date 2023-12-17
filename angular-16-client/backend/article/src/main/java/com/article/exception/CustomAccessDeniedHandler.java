package com.article.exception;

import com.article.entity.Role;
import com.article.entity.User;
import com.article.util.Helper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
                     AccessDeniedException accessDeniedException) throws IOException, ServletException {
    User currentUser = Helper.getCurrentUser();
    String rolename = "Anonymous Role";
    if (Objects.nonNull(currentUser)) {
      for (Role privilege : currentUser.getPrivileges()) {
        rolename = privilege.getName().name();
      }

    }

    // Get the request URL
    String requestURL = request.getRequestURI();
    String method = request.getMethod();


    System.out.println(requestURL + "  pp ");
    Map<String, Object> body = new HashMap<>();
    body.put("status", HttpStatus.FORBIDDEN.value());
    body.put("error", "Access Denied");
    body.put("message", rolename + " don't have permission to access this " + requestURL + " " + method);

    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType("application/json");

    OutputStream outputStream = response.getOutputStream();
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(outputStream, body);
    outputStream.flush();
  }
}


