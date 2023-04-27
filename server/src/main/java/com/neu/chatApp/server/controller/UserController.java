package com.neu.chatApp.server.controller;

import com.neu.chatApp.server.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
  public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
    return userService.login(request.get("username"), request.get("password"));
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody Map<String, String> request) {
    return userService.signup(request.get("username"), request.get("password"));
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestBody Map<String, String> request) {
    return userService.logout(request.get("username"));
  }
}
