package com.neu.chatApp.controller;

import com.neu.chatApp.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/user")
  public String user() {
    return "user";
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody String username, @RequestBody String password) {
    return userService.login(username, password);
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody String username, @RequestBody String password) {
    return userService.signup(username, password);
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestBody String username) {
    return userService.logout(username);
  }
}
