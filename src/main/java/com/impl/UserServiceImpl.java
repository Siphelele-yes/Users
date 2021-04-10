package com.impl;

import com.model.User;
import com.repository.UserRepository;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findUser(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findByCriteria(String criteria, String searchItem) {

        switch (criteria){
            case "username" :
                return this.userRepository.findByUsername(searchItem);
            case "firstName" :
                return this.userRepository.findByFirstName(searchItem);
            case "lastName" :
                return this.userRepository.findByLastName(searchItem);
            case "age" :
                try {
                    Integer age = Integer.valueOf(searchItem);
                    return this.userRepository.findByAge(age);
                }
                catch (NumberFormatException e){
                    System.out.println("Could not convert age to number");
                }
                return new ArrayList<>();
            case "country" :
                return this.userRepository.findByCountry(searchItem);

        }
        return new ArrayList<>();
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
