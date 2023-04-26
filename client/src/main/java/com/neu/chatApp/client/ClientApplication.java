package com.neu.chatApp.client;

import com.neu.chatApp.client.data.ClientData;
import com.neu.chatApp.client.gui.GUIMain;
import com.sun.tools.javac.Main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import logger.SimpleLogger;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {
  private static String serverHostname;
  private static int serverHTTPPort;

  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Please run the application with <server-hostname> <server-http-port>");
      System.exit(1);
    }

    serverHostname = args[0];
    serverHTTPPort = Integer.parseInt(args[1]);

    ClientData.baseURL = "http://" + serverHostname + ":" + serverHTTPPort + "/user/api";

    new SpringApplicationBuilder(ClientApplication.class).web(WebApplicationType.NONE).run(args);
  }

  @Override
  public void run(String... args) throws Exception {
    SimpleLogger.info("ClientApplication started");
    new GUIMain();
  }
}
