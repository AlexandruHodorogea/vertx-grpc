package com.housework.vertxrpc.client;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.Scanner;


@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private Client client;

    Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        System.out.println(args);
        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
    }

    @Override
    public void run(String... args) {
        logger.info("EXECUTING : command line runner");

        Scanner scanner = new Scanner(System.in);
        String name=scanner.nextLine();
        while(!name.isEmpty()) {
            client.sayHello(name);
            name=scanner.nextLine();
        }

        logger.info("EXECUTING : command line runner stopped");
    }
}
