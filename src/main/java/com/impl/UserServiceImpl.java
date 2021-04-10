package com.impl;

import com.model.User;
import com.repository.UserRepository;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }

    @Override
    public Optional<User> findUser(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public Optional<User> deleteUser(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
           userRepository.delete(optionalUser.get());
            return optionalUser;
        }
        else
            return Optional.empty();

    }

    @Override
    public Optional<User> updateUser(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if(optionalUser.isPresent()){
            User existingUser = optionalUser.get();

            if (user.getUsername()!= null){
                existingUser.setUsername(user.getUsername());
            }
            if (user.getPassword()!= null){
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getFirstName()!= null){
                existingUser.setFirstName(user.getFirstName());
            }
            if (user.getLastName()!= null){
                existingUser.setLastName(user.getLastName());
            }
            if (user.getAge() != null) {
                existingUser.setAge(user.getAge());
            }
            if (user.getCountry()!=null){
                existingUser.setCountry(user.getCountry());
            }
            userRepository.save(existingUser);
            return Optional.of(existingUser);
        }
        return Optional.empty();
    }

}
