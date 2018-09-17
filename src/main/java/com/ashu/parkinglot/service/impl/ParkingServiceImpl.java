package com.ashu.parkinglot.service.impl;

import com.ashu.parkinglot.model.ParkingSlot;
import com.ashu.parkinglot.repository.ParkingRepository;
import com.ashu.parkinglot.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    @Override
    public void parkVehicle(String regNo, String color) {

        List<ParkingSlot> vehicleList = parkingRepository.findAll();
        if (!vehicleList.isEmpty()) {
            Optional<Integer>  optionalInteger = vehicleList.stream().filter(value -> !value.isOccupied()).map(ParkingSlot::getSlotNo).min(Comparator.naturalOrder());

            if (optionalInteger.isPresent()){
                ParkingSlot vehicle = new ParkingSlot(optionalInteger.get());
                vehicle.setOccupied(true);
                vehicle.setRegistrationNo(regNo.toUpperCase());
                vehicle.setColor(StringUtils.capitalize(color));
                try {
                    parkingRepository.save(vehicle);
                    System.out.println("Allocated slot number: " + vehicle.getSlotNo());
                } catch (DataIntegrityViolationException e) {
                    System.out.println("Vehicle with same registration no already present");
                }
            }else {
                System.out.println("Sorry, parking lot is full");
            }
        } else {
            System.out.println("Please create a parking lot first");
        }
    }

    @Override
    public void getParkingStatus() {
        List<ParkingSlot> vehicleList = parkingRepository.findAllByOccupiedTrue();
        if (vehicleList.isEmpty()) {
            System.out.println("All parking slots are available");
        } else {
            System.out.println("Slot No.    Registration No.    Colour");
            vehicleList.forEach(System.out::println);
        }
    }

    @Override
    @Transactional
    public void freeParkingLot(int slot) {
        ParkingSlot vehicle = parkingRepository.findBySlotNo(slot);
        if (vehicle != null) {
            if (vehicle.isOccupied()) {
                parkingRepository.updateOccupancyBySlotNo(false, slot);
                System.out.println("Slot number " + vehicle.getSlotNo() + " is free");
            } else {
                System.out.println("Slot number " + vehicle.getSlotNo() + " is already free");
            }
        } else {
            System.out.println("Invalid slot no " + slot);
        }
    }

    @Override
    public void createParkingSlot(int slots) {
        if(slots < 1) {
            System.out.println("Cannot create parking lot with " + slots + " slots");
            return;
        }
        parkingRepository.deleteAll();
        for (int i = 1; i <= slots; i++) {
            ParkingSlot vehicle = new ParkingSlot();
            vehicle.setSlotNo(i);
            vehicle.setOccupied(false);
            parkingRepository.save(vehicle);
        }
        System.out.println("Created a parking lot with " + slots + " slots");
    }

    @Override
    public void getRegNoForColour(String colour) {
        List<ParkingSlot> vehicles = parkingRepository.findAllByOccupiedTrueAndColor(StringUtils.capitalize(colour));
        if (!vehicles.isEmpty()) {
            List<String> regNoList = vehicles.stream().map(ParkingSlot::getRegistrationNo).collect(Collectors.toList());
            System.out.println(String.join(", ", regNoList));
        } else {
            System.out.println("Not found");
        }
    }

    @Override
    public void getSlotNoForColour(String colour) {
        List<ParkingSlot> vehicles = parkingRepository.findAllByOccupiedTrueAndColor(StringUtils.capitalize(colour));
        if (!vehicles.isEmpty()) {
            List<String> slotNoList = vehicles.stream().map(k -> k.getSlotNo() + "").collect(Collectors.toList());
            System.out.println(String.join(", ", slotNoList));
        } else {
            System.out.println("Not found");
        }
    }

    @Override
    public void getSlotNoForRegNo(String regNo) {
        ParkingSlot vehicle = parkingRepository.findByOccupiedTrueAndRegistrationNo(regNo.toUpperCase());
        if (vehicle != null) {
            System.out.println(vehicle.getSlotNo());
        } else {
            System.out.println("Not found");
        }
    }
}
