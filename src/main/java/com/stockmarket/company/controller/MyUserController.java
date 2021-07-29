package com.stockmarket.company.controller;

import com.stockmarket.company.entity.MyUser;
import com.stockmarket.company.service.MyUserService;
import com.stockmarket.company.service.MyUserServiceImpl;
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

    // Get a user by {username}
    @GetMapping("/users/{username}")
    public ResponseEntity<MyUser> getUser1(@PathVariable String username) {

        MyUser myUser = myUserService.getUser(username);
        return new ResponseEntity<MyUser>(myUser, null, HttpStatus.OK);
    }

    // Update User - {userId}
    @PutMapping("/users/update/{username}")
    public ResponseEntity<MyUser> updateUser1(@PathVariable String username, @Valid @RequestBody MyUser myUser, BindingResult bindingResult) {
        MyUser updatedMyUser = myUserService.updateUser(username, myUser);
        return new ResponseEntity<MyUser>(updatedMyUser, null, HttpStatus.OK);
    }

    // Delete User - {userId}
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<HttpStatus> removeUser1(@PathVariable Long userId) {
        myUserService.removeUser(userId);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    // Confirm User
    @GetMapping(value="/confirmuser/{username}")
    public ResponseEntity<String> confirmUser(@PathVariable String username) {
        String response = myUserService.confirmUser(username);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

}
