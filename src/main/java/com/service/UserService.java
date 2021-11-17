package com.service;

import com.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    public Page<User> findAll(Pageable pageable);

    public Optional<User> findUser(int id);

    public User addUser (User user);

    public Optional<User> deleteUser(int id);

    public Optional<User> updateUser(User user);

    public User findUserByUsername(String username);



}
