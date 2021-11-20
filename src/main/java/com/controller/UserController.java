package com.controller;

import com.config.PasswordConfig;
import com.model.User;
import com.repository.UserRepository;
import com.service.UserService;
import org.assertj.core.presentation.Representation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMap;
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

  /*
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

    @RequestMapping(value = "/login-user", method = RequestMethod.POST
            , produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_FORM_URLENCODED_VALUE}
            , consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<?> loginUser(@Validated  User user)  {
        HttpStatus status ;
        User usernameExist = userService.findUserByUsername(user.getUsername());
        if(!usernameExist.getPassword().isEmpty() && usernameExist.getPassword().equals(user.getPassword())){
            status = HttpStatus.OK;
        }else
            status = HttpStatus.FORBIDDEN;

        return new ResponseEntity<>(usernameExist,status);
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST
            , produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_FORM_URLENCODED_VALUE}
            , consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<?> forgotPassword(@Validated  User user)  {
        HttpStatus status;
        User usernameExist = userService.findUserByUsername(user.getUsername());
        if(!usernameExist.getUsername().isEmpty()){
            status = HttpStatus.OK;
        }else
            status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(status);
    }



    @GetMapping("/users")
    public ResponseEntity<?> findAll(Pageable pageable){
        return new ResponseEntity<Page<User>>(userService.findAll(pageable), HttpStatus.OK);
    }


    @RequestMapping(value = "/register-user",method = RequestMethod.POST
            , produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_FORM_URLENCODED_VALUE}
            , consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<?> add (@Validated  User user){
        User newUser = userService.addUser(user);
        return new ResponseEntity<User>(newUser,HttpStatus.CREATED);
    }

}
