package com.article.service;

import com.article.constant.ERole;
import com.article.dto.SignUpDto;
import com.article.entity.Role;
import com.article.entity.User;
import com.article.repository.RoleRepository;
import com.article.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }


  @Override
  public User findByUserId(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
  }

  @Override
  public List<User> findAllByIdIn(Collection<Long> authorIds) {
    return userRepository.findAllByIdIn(authorIds);
  }

  @Override
  public Long register(SignUpDto signUpDto) {
    User user = convertToEntity(signUpDto);
    return user.getId();

  }

  private User convertToEntity(SignUpDto signUpDto) {

    Set<Role> roleSet = new HashSet<>();
    Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);
    role.ifPresent(roleSet::add);
    User user = new User();
    user.setUsername(signUpDto.getUsername());
    user.setEmail(signUpDto.getEmail());
    user.setMobileNumber(signUpDto.getMobile());
    user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
    user.setPrivileges(roleSet);

    return user;
  }
}
