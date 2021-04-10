package com.controller;

import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
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

    @GetMapping("/search")
    public ResponseEntity<?> findByCriteria (@RequestParam (name = "criteria",required = true ) String criteria,
                                             @RequestParam (name = "searchItem",required = true )String searchItem){
        return new ResponseEntity<List <User>>  (userService.findByCriteria(criteria, searchItem),HttpStatus.OK);
    }

}
