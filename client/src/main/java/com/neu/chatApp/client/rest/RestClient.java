package com.neu.chatApp.client.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.chatApp.client.data.ClientData;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.web.client.*;

import java.util.HashMap;
import java.util.Map;

public class RestClient {
    private RestTemplate restTemplate;

    public RestClient() {
        this.restTemplate = new RestTemplate();
    }

    /** Use for new user signup
     *
     */
    public String signup(@NotNull String username, @NotNull String email, @NotNull String password)
            throws HttpClientErrorException, HttpServerErrorException, ResourceAccessException {
        String url = ClientData.baseURL + "/signup";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("email", email);
        body.put("password", password);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        return responseEntity.getBody();
    }

    /**
     * Use for use login
     */
    public Map<String, Object> login(String email, String password, @NotNull String hostname, @NotNull int port)
        throws HttpClientErrorException, HttpServerErrorException, ResourceAccessException {
        String url = ClientData.baseURL + "/login";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        body.put("hostname", hostname);
        body.put("port", port);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> responseEntity;
        Map<String, Object> result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            responseEntity = restTemplate.postForEntity(url, request, String.class);
            // TODO: read value change to another method???(1)
            result = mapper.readValue(responseEntity.getBody(), Map.class);
        }
        catch (HttpClientErrorException e) {
            try {
                result = mapper.readValue(e.getResponseBodyAsString(), Map.class);
            }
            catch (JsonProcessingException exception) {
                exception.printStackTrace();
            }
            assert result != null;
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void logout(Long userId) {
        new RestTemplate().postForEntity(ClientData.baseURL + "/logout", userId, Void.class);
    }

}
