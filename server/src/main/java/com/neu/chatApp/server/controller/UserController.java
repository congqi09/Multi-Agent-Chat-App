package com.neu.chatApp.server.controller;

import com.neu.chatApp.server.service.UserService;

import com.neu.chatApp.entity.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

//  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
    return userService.login(request.get("username"), request.get("password"));
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
    return userService.login(username, password);
  }

//  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody Map<String, String> request) {
    return userService.signup(request.get("username"), request.get("password"));
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestParam String username, @RequestParam String password) {
    return userService.signup(username, password);
  }

//  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestBody Map<String, String> request) {
    return userService.logout(request.get("username"));
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestParam String username) {
    return userService.logout(username);
  }

  @PostMapping("/getMessages")
  public ResponseEntity<List<Message>> getMessages() {
    return userService.getMessages();
  }

  @PostMapping("/sendMessage")
  public ResponseEntity<String> sendMessage(@RequestParam String username, @RequestParam String message) {
    return userService.sendMessage(username, message);
  }
}
