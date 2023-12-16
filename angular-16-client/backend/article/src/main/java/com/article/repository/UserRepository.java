package com.article.repository;

import com.article.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String admin);

    List<User> findAllByIdIn(Collection<Long> authorIds);

}
