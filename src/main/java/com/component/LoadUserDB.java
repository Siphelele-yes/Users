package com.component;

import com.configuration.SecurityConfig;
import com.model.User;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
public class LoadUserDB implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User("tDlamini", UUID.randomUUID().toString(),"Thando", "Dlamini", 9, "South Africa");
        User user2 = new User("mKhoza", UUID.randomUUID().toString(),"Mihle", "Khoza", 10, "Botswana");
        User user3 = new User("sSigenu", UUID.randomUUID().toString(),"Sabi", "Sigenu", 11, "Nigeria");

        List<User> userList = Arrays.asList(user1, user2, user3);

        userRepository.saveAll(userList);
    }
}
