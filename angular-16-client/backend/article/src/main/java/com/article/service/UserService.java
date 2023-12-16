package com.article.service;

import com.article.dto.SignUpDto;
import com.article.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserService {
    User findByUserId(Long id);

    List<User> findAllByIdIn(Collection<Long> authorIds);

    Long register(SignUpDto signUpDto);
}
