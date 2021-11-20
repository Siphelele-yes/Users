package com.impl;

import com.model.User;
import com.repository.UserRepository;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable) ;
    }

    @Override
    public Optional<User> findUser(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User addUser(User user) {
        userRepository.save(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.findUserByUsername(user.getUsername());
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
                existingUser.setPassword(user.getPassword());
            }
            if (user.getName()!= null){
                existingUser.setName(user.getName());
            }
            if (user.getSurname()!= null){
                existingUser.setSurname(user.getSurname());
            }
            userRepository.save(existingUser);
            return Optional.of(existingUser);
        }

        return Optional.empty();
    }

    @Override
    public User findUserByUsername(String username) {
      return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional findUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }
}
