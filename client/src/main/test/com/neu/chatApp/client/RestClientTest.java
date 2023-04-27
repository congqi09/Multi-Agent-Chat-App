package com.neu.chatApp.client;

import com.neu.chatApp.client.rest.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RestClientTest {

    @Test
    public void testSignUp() {
        RestClient restClient = new RestClient("http://localhost:8080/api/user/signup");
        String result = restClient.signUp("test", "test");
        Assertions.assertNotNull(result);
    }

    @Test
    public void testLogin() {
        RestClient restClient = new RestClient("http://localhost:8080/api/user/login");
        restClient.signUp("test", "test");
        String result = restClient.login("test", "test");
        Assertions.assertNotNull(result);
    }

    @Test
    public void testLogout() {
        RestClient restClient = new RestClient("http://localhost:8080/api/user");
        restClient.signUp("test", "test");
        restClient.login("test", "test");
        String result = restClient.logout("test");
        Assertions.assertNotNull(result);
    }

    @Test
    public void testSendMessage() {
        RestClient restClient = new RestClient("http://localhost:8080/api/user");
        restClient.signUp("test", "test");
        restClient.login("test", "test");
        String result = restClient.sendMessage("test", "test message");
        Assertions.assertNotNull(result);
    }

    @Test
    public void testGetMessages() {
        RestClient restClient = new RestClient("http://localhost:8080/api/user");
        restClient.signUp("test", "test");
        restClient.login("test", "test");
        restClient.sendMessage("test", "test message");
        String result = restClient.getMessages();
        Assertions.assertNotNull(result);
    }
}

