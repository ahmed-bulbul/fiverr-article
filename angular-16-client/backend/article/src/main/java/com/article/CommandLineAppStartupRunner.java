package com.article;

import com.article.constant.ERole;
import com.article.entity.Role;
import com.article.entity.User;
import com.article.repository.RoleRepository;
import com.article.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) throws Exception {
        createRole(ERole.ROLE_ADMIN);
        createRole(ERole.ROLE_USER);
        createAdminUser();
        createNormalUser();
    }

    void createRole(ERole role){
        Optional<Role> roleInst = roleRepository.findByName(role);
        if(roleInst.isEmpty()) {
            Role entity = new Role(role);
            roleRepository.save(entity);
        }
    }

    void createAdminUser(){
        Optional<User> user = userRepository.findByUsername("admin");
        Set<Role> roleSet = new HashSet<>();
        Optional<Role> role = roleRepository.findByName(ERole.ROLE_ADMIN);
        role.ifPresent(roleSet::add);

        if(user.isEmpty()){
            User newUser = new User("admin","01753155400","admin@gmail.com",
                    passwordEncoder.encode("123456"));
            newUser.setPrivileges(roleSet);
            userRepository.save(newUser);
        }
    }

    void createNormalUser(){
        Optional<User> user = userRepository.findByUsername("user");
        Set<Role> roleSet = new HashSet<>();
        Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);
        role.ifPresent(roleSet::add);

        if(user.isEmpty()){
            User newUser = new User("user","01753155401","user@gmail.com",
                    passwordEncoder.encode("123456"));
            newUser.setPrivileges(roleSet);
            userRepository.save(newUser);
        }
    }

}
