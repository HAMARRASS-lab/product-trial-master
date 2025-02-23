package com.back.backend.controllers;

import com.back.backend.mapper.UserDTO;
import com.back.backend.entities.User;
import com.back.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserRestController {

    @Autowired
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/addUserAccount")
    public ResponseEntity<User> addUser(@RequestBody User user) {
      return ResponseEntity.ok(userService.addUser(user));
    }

}
