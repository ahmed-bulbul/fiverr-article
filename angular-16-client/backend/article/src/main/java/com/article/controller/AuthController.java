package com.article.controller;

import com.article.dto.LoginDto;
import com.article.repository.RoleRepository;
import com.article.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> authenticateUser() {
    Map<String, String> response = new HashMap<>();
    response.put("message", "User login successfully!...");
    return ResponseEntity.ok(response);
  }


  @PostMapping("/logout")
//    @PreAuthorize("hasAnyRole('USER', 'ROLE_ADMIN')")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> logoutUser() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("User logout successfully!...", HttpStatus.OK);
    }
}
