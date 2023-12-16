package com.article.service;

import com.article.entity.User;
import com.article.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User findByUserId(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Override
    public List<User> findAllByIdIn(Collection<Long> authorIds) {
        return userRepository.findAllByIdIn(authorIds);
    }
}
