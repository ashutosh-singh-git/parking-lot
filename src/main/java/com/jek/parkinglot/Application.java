package com.jek.parkinglot;

import com.jek.parkinglot.component.CommandHandler;
import com.jek.parkinglot.util.Commands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

    @Autowired
    private CommandHandler commandHandler;

    @Override
    public void run(String... strings) {
        Scanner scanIn = new Scanner(System.in);
        String text;
        System.out.println("Welcome !");
        do {
            System.out.print("$ ");
            text = scanIn.nextLine();
            String[] arguments = text.trim().split(" ");
            if (Commands.commandsSet.contains(arguments[0])) {
                commandHandler.executeCommand(arguments);
            } else {
                System.out.println("Unknown command '"+ arguments[0] +"'.");
            }
        } while (!"EXIT".equalsIgnoreCase(text));
        scanIn.close();
    }
}
