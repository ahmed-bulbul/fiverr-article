package com.article.service;

import com.article.entity.User;

public interface UserService {
    User findByUserId(Long id);
}
