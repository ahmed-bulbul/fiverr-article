package com.article.security;

import com.article.entity.Role;
import com.article.entity.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CustomUserDetails implements UserDetails {
  private static final String ERROR_MESSAGE = "Your role has not been assigned by the Administrator. Please contact the administrator for role assignment.";

  @Getter
  private Long id;
  private String username;
  private String password;
  @Getter
  private String name;
  private String email;
  private String mobile;
  @Getter
  private Set<Role> privileges;
  private Collection<? extends GrantedAuthority> authorities;

  public CustomUserDetails(Long id, String username, String password, String name, String email, String mobile, Set<Role> privileges, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.name = name;
    this.email = email;
    this.mobile = mobile;
    this.privileges = privileges;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public static CustomUserDetails build(User user) {

    Set<Role> roleList = user.getPrivileges();
    if (Objects.isNull(roleList)) {
      throw new RuntimeException(ERROR_MESSAGE);
    }

    List<GrantedAuthority> authorities = roleList.stream().map(r ->
        List.of(new SimpleGrantedAuthority(r.getName().name()))).toList()
      .stream().flatMap(Collection::stream).collect(Collectors.toList());

    return new CustomUserDetails(
      user.getId(),
      user.getUsername(),
      user.getPassword(),
      user.getName(),
      user.getEmail(), user.getMobileNumber(),
      user.getPrivileges(),
      authorities);
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
