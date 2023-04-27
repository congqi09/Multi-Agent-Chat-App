package com.neu.chatApp.client;

import com.neu.chatApp.client.data.ClientData;
import com.neu.chatApp.client.gui.GUIMain;
import com.sun.tools.javac.Main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import logger.SimpleLogger;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Slf4j
public class ClientApplication implements CommandLineRunner {
  private static String serverHostname;
  private static int serverHTTPPort;

  public static void main(String[] args) {
//    if (args.length != 2) {
//      System.out.println("Please run the application with <server-hostname> <server-http-port>");
//      System.exit(1);
//    }

//    serverHostname = args[0];
//    serverHTTPPort = Integer.parseInt(args[1]);
    serverHostname = "localhost";
    serverHTTPPort = 8080;

    ClientData.baseURL = "http://" + serverHostname + ":" + serverHTTPPort + "/api/user";

    new SpringApplicationBuilder(ClientApplication.class).web(WebApplicationType.NONE).run(args);
  }

  @Override
  public void run(String... args) throws Exception {
    SimpleLogger.info("ClientApplication started");
    //new GUIMain();
    new Thread((Runnable) new GUIMain()).start();
  }
}
