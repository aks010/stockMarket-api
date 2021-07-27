package com.stockmarket.company.controller;

import com.stockmarket.company.entity.MyUser;
import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MyUserController {

    @Autowired
    private MyUserService myUserService;

    // List Existing Users
    @GetMapping("/users/list")
    public Page<MyUser> listMyUsers(Pageable pageable) {
        return myUserService.listUsers(pageable);
    }

    // Create new User
//    @PostMapping("/users/new")
//    public ResponseEntity<MyUsers> newUser1(@Valid @RequestBody MyUser myUser, BindingResult bindingResult) {
//            if(bindingResult.hasErrors()) {
//                throw new BadRequestException("Please submit with valid entries!");
//            }
//
//            System.out.println(bindingResult);
//
//            MyUser newMyUser = myUserService.newUser(myUser);
//            return new ResponseEntity<MyUser>(newMyUser, null, HttpStatus.CREATED);
//    }

    // Create new User
//    @PostMapping("/users/login")
//    public ResponseEntity<MyUser> loginMyUser(@Valid @RequestBody MyUser myUser, BindingResult bindingResult) {
//        if(myUser.getEmail() == null || myUser.getEmail() == "" || myUser.getPassword()==null || myUser.getPassword()=="" ) {
//            throw new BadRequestException("Please submit with valid entries!");
//        }
//
//        MyUser newMyUser = myUserService.loginMyUser(myUser);
//        return new ResponseEntity<MyUser>(newMyUser, null, HttpStatus.CREATED);
//    }

    // Get a user by {userId}
    @GetMapping("/users/{userId}")
    public ResponseEntity<MyUser> getUser1(@PathVariable Long userId) {
        MyUser myUser = myUserService.getUser(userId);
        return new ResponseEntity<MyUser>(myUser, null, HttpStatus.OK);
    }

    // Update User - {userId}
    @PutMapping("/users/update/{userId}")
    public ResponseEntity<MyUser> updateUser1(@PathVariable Long userId, @Valid @RequestBody MyUser myUser, BindingResult bindingResult) {
        MyUser updatedMyUser = myUserService.updateUser(userId, myUser);
        return new ResponseEntity<MyUser>(updatedMyUser, null, HttpStatus.OK);
    }

    // Delete User - {userId}
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<HttpStatus> removeUser1(@PathVariable Long userId) {
        myUserService.removeUser(userId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    // Confirm User with id - {userId}
    @GetMapping(value="/confirmuser/{username}")
    public ResponseEntity<String> confirmUser(@PathVariable String username) {
        String response = myUserService.confirmUser(username);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

}
