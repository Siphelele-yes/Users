package com.component;

import com.configuration.SecurityConfig;
import com.model.User;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional
public class LoadUserDB implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User("tDlamini", "1Thando1" +
                "","Thando", "Dlamini", 9, "South Africa");

        List<User> userList = Arrays.asList(user1);
        userList = userList.stream().collect(Collectors.toList());

        userRepository.saveAll(userList);
    }
}
