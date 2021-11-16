package com.controller;

import com.config.PasswordConfig;
import com.model.User;
import com.repository.UserRepository;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    PasswordConfig passwordConfig;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

  /*  @GetMapping("/users")
    public ResponseEntity<?> findAll(Pageable pageable){
        return new ResponseEntity<Page<User>>(userService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<?> findById(@RequestParam (name = "id",required = true ) int id){
        Optional <User> optionalUser = userService.findUser(id);
        if(optionalUser.isPresent()){
            return new ResponseEntity <User> (optionalUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

    }
    
    @PostMapping("/add")
    public ResponseEntity<?> add (@Validated @RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete (@RequestParam (name = "id",required = true ) int id){
        Optional <User> optionalUser = userService.deleteUser(id);
        if(optionalUser.isPresent()){
            return new ResponseEntity <User> (optionalUser.get(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update (@Validated @RequestBody User user){
        Optional <User> optionalUser = userService.updateUser(user);
        if(optionalUser.isPresent())
            return new ResponseEntity <User> (optionalUser.get(), HttpStatus.NO_CONTENT);
        return new ResponseEntity <User> (HttpStatus.NOT_FOUND);
    }

*/


    @PostMapping("/login-user")
    @ResponseBody
    public ResponseEntity<?> loginUser(@Validated @RequestBody User user){

        User usernameExist = userService.findUserByUsername(user.getUsername());
        String existingPassword =usernameExist.getPassword();
        String currentPassword=user.getPassword();

        if (usernameExist.getUsername().isEmpty()) {
            return new ResponseEntity<>("\"Oops.! User email not found, please register.\"",HttpStatus.OK);
        }else if (passwordConfig.passwordDecoder(currentPassword,existingPassword)) {
            return new ResponseEntity<>("\"Password Exists, logged-in\"",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("\"Password didn't match, please enter the correct password, logged-in\"",HttpStatus.OK);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAll(Pageable pageable){
        return new ResponseEntity<Page<User>>(userService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> add (@Validated @RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }


}
