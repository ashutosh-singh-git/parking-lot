package com.jek.parkinglot.component;

import com.jek.parkinglot.service.ParkingService;
import com.jek.parkinglot.util.Commands;
import org.springframework.stereotype.Component;

@Component
public class CommandHandler {

    private final ParkingService parkingService;

    public CommandHandler(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    public void executeCommand(String[] arguments) {

        try {
            switch (arguments[0]) {
                case Commands.CREATE:
                    parkingService.createParkingSlot(Integer.valueOf(arguments[1]));
                    break;
                case Commands.PARK:
                    parkingService.parkVehicle(arguments[1], arguments[2]);
                    break;
                case Commands.LEAVE:
                    parkingService.freeParkingLot(Integer.valueOf(arguments[1]));
                    break;
                case Commands.STATUS:
                    parkingService.getParkingStatus();
                    break;
                case Commands.REG_NO_FOR_COLOUR:
                    parkingService.getRegNoForColour(arguments[1]);
                    break;
                case Commands.SLOT_NO_FOR_REG_NO:
                    parkingService.getSlotNoForRegNo(arguments[1]);
                    break;
                case Commands.SLOT_NO_FOR_COLOUR:
                    parkingService.getSlotNoForColour(arguments[1]);
                    break;
                case Commands.EXIT:
                    System.out.println("Bye Bye");
                    break;
                default:
                    System.out.println("Command not found!. Type 'exit' to terminate");
                    break;
            }
        } catch (Exception e) {
            //Nothing to do here.
        }
    }
}
