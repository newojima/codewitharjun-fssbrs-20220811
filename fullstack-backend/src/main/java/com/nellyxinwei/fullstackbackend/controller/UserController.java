package com.nellyxinwei.fullstackbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nellyxinwei.fullstackbackend.exception.UserNotFoundException;
import com.nellyxinwei.fullstackbackend.model.User;
import com.nellyxinwei.fullstackbackend.repository.UserRepository;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/user")
  User newUser(@RequestBody User newUser) {
    return userRepository.save(newUser);
  }

  @GetMapping("/users")
  List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/user/{id}")
  User getUserById(@PathVariable Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));
  }

  @PutMapping("/user/{id}")
  User updatedUser(@RequestBody User newUser, @PathVariable Long id) {
    return userRepository.findById(id)
        .map(user -> {
          user.setUsername(newUser.getName());
          user.setName(newUser.getUsername());
          user.setEmail(newUser.getEmail());
          return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException(id));
  }

}
