package com.neu.chatApp.client.rest;

import com.neu.chatApp.client.data.ClientData;

import logger.SimpleLogger;
import okhttp3.*;
import java.io.IOException;

public class RestClient {

  // one instance, reuse
  private final OkHttpClient httpClient;
  private String baseUrl;

  public RestClient(String baseUrl) {
    this.httpClient = new OkHttpClient();
    this.baseUrl = baseUrl;
  }

  private Response sendPostRequest(String url, RequestBody body) {
    Request request = new Request.Builder()
            .url(url)
            .addHeader("Content-Type", "OkHttp Bot")
            .post(body)
            .build();

    try (Response response = httpClient.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        SimpleLogger.error("Unexpected code " + response);
      }
      SimpleLogger.info(response.body().string());
      return response;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Response signUp(String username, String password) {
    RequestBody formBody = new FormBody.Builder()
            .add("username", username)
            .add("password", password)
            .build();

    return sendPostRequest(baseUrl + "/signup", formBody);
  }

  public Response login(String username, String password) {
    RequestBody formBody = new FormBody.Builder()
            .add("username", username)
            .add("password", password)
            .build();

    return sendPostRequest(baseUrl + "/login", formBody);
  }


  public Response logout(String username) {
    RequestBody formBody = new FormBody.Builder()
            .add("username", username)
            .build();

    return sendPostRequest(baseUrl + "/logout", formBody);
  }

  public Response sendMessage(String username, String message) {
    // TODO: changed formBody to jsonBody
    RequestBody formBody = new FormBody.Builder()
            .add("username", username)
            .add("message", message)
            .build();

    return sendPostRequest(baseUrl + "/sendMessage", formBody);
  }

  public Response getMessages() {
    RequestBody formBody = new FormBody.Builder()
            .build();

    return sendPostRequest(baseUrl + "/getMessages", formBody);
  }

  public static void main(String[] args) {
    RestClient test = new RestClient("http://localhost:8080/api/user");
    test.signUp("test-ok-http", "test");
    test.login("test-ok-http", "test");
    test.logout("test-ok-http");
    test.signUp("test-ok-http3", "test");
    test.sendMessage("test-ok-http3", "test message");
    test.sendMessage("test-ok-http", "test message2");
    // validate if user exists
    test.getMessages();
  }
}