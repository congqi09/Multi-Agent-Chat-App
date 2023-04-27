package com.neu.chatApp.client;

import com.neu.chatApp.entity.Message;
import com.neu.chatApp.server.controller.UserController;
import com.neu.chatApp.server.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserControllerTest {

    private final UserService userService = Mockito.mock(UserService.class);
    private final UserController userController = new UserController(userService);

    @Test
    public void testUser() {
        String result = userController.user();
        Assertions.assertEquals("user", result);
    }

    @Test
    public void testLoginWithRequestBody() {
        Map<String, String> request = new HashMap<>();
        request.put("username", "test");
        request.put("password", "test");
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("success", HttpStatus.OK);

        Mockito.when(userService.login("test", "test")).thenReturn(expectedResponse);

        ResponseEntity<String> result = userController.login(request);
        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    public void testLoginWithRequestParam() {
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("success", HttpStatus.OK);

        Mockito.when(userService.login("test", "test")).thenReturn(expectedResponse);

        ResponseEntity<String> result = userController.login("test", "test");
        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    public void testSignupWithRequestBody() {
        Map<String, String> request = new HashMap<>();
        request.put("username", "test");
        request.put("password", "test");
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("success", HttpStatus.OK);

        Mockito.when(userService.signup("test", "test")).thenReturn(expectedResponse);

        ResponseEntity<String> result = userController.signup(request);
        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    public void testSignupWithRequestParam() {
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("success", HttpStatus.OK);

        Mockito.when(userService.signup("test", "test")).thenReturn(expectedResponse);

        ResponseEntity<String> result = userController.signup("test", "test");
        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    public void testLogoutWithRequestBody() {
        Map<String, String> request = new HashMap<>();
        request.put("username", "test");
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("success", HttpStatus.OK);

        Mockito.when(userService.logout("test")).thenReturn(expectedResponse);

        ResponseEntity<String> result = userController.logout(request);
        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    public void testLogoutWithRequestParam() {
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("success", HttpStatus.OK);

        Mockito.when(userService.logout("test")).thenReturn(expectedResponse);

        ResponseEntity<String> result = userController.logout("test");
        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    public void testGetMessages() {
        List<Message> expectedMessages = new ArrayList<>();
        expectedMessages.add(new Message("user1", "test1"));
        expectedMessages.add(new Message("user2", "test2"));
        ResponseEntity<List<Message>> expectedResponse = new ResponseEntity<>(expectedMessages, HttpStatus.OK);

        Mockito.when(userService.getMessages()).thenReturn(expectedResponse);

        ResponseEntity<List<Message>> result = userController.getMessages();
        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    public void testSendMessage() {
        UserService userService = Mockito.mock(UserService.class);
        UserController userController = new UserController(userService);

        String username = "testUser";
        String message = "Hello World";

        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Message sent successfully", HttpStatus.OK);

        Mockito.when(userService.sendMessage(username, message)).thenReturn(expectedResponse);

        ResponseEntity<String> response = userController.sendMessage(username, message);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedResponse.getBody(), response.getBody());

        Mockito.verify(userService, Mockito.times(1)).sendMessage(username, message);
    }
}
