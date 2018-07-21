package com.jek.parkinglot;

import com.jek.parkinglot.service.ParkingService;
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

    @Override
    public void run(String... strings) {
        Scanner scanIn = new Scanner(System.in);
        String text;
        System.out.println("Welcome !");
        do {
            System.out.print("$ ");
            text = scanIn.nextLine();
            String[] arguments = text.split(" ");
            if (Commands.commandsSet.contains(arguments[0])) {
                executeCommand(arguments);
            } else {
                System.out.println("Unknown command '"+ arguments[0] +"'.");
            }
        } while (!"EXIT".equalsIgnoreCase(text));
        scanIn.close();
    }

    @Autowired
    private ParkingService parkingService;

    private void executeCommand(String[] arguments) {

        try {
            switch (arguments[0]) {
                case Commands.CREATE:
                    parkingService.createParkingSlot(Integer.valueOf(arguments[1]));
                    break;
                case Commands.PARK:
                    parkingService.parkVehicle(arguments[1], arguments[2]);
                    break;
                case Commands.STATUS:
                    parkingService.getParkingStatus();
                    break;
                case Commands.LEAVE:
                    parkingService.freeParkingLot(Integer.valueOf(arguments[1]));
                    break;
                default:
                    System.out.println("Command not found!. Type 'exit' to terminate");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
