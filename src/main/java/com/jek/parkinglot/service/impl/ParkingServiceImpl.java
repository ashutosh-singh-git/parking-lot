package com.jek.parkinglot.service.impl;

import com.jek.parkinglot.model.Vehicle;
import com.jek.parkinglot.repository.ParkingRepository;
import com.jek.parkinglot.service.ParkingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public void parkVehicle(String regNo, String color) {

        Vehicle vehicle = parkingRepository.findFirstByOccupiedFalse();
        if (vehicle != null) {
            vehicle.setOccupied(true);
            vehicle.setRegistrationNo(regNo);
            vehicle.setColor(color);
            parkingRepository.save(vehicle);
            System.out.println("Allocated slot number: " + vehicle.getSlotNo());
        } else {
            System.out.println("Sorry, parking lot is full");
        }
    }

    @Override
    public void getParkingStatus() {
        List<Vehicle> vehicleList = parkingRepository.findAllByOccupiedTrue();
        if(vehicleList.isEmpty()){
            System.out.println("All parking slots are available !!");
        }else {
            System.out.println("Slot No.    Registration No.    Colour");
            vehicleList.forEach(System.out::println);
        }
    }

    @Override
    @Transactional
    public void freeParkingLot(int slot) {
        Vehicle vehicle = parkingRepository.findBySlotNo(slot);
        if(vehicle != null){
            if(vehicle.isOccupied()){
                parkingRepository.updateOccupancyBySlotNo(false,slot);
                System.out.println("Slot number " + vehicle.getSlotNo() + " is free");
            }else {
                System.out.println("Slot number " + vehicle.getSlotNo() + " is already free");
            }
        }else {
            System.out.println("Invalid slot no " + slot);
        }
    }

    @Override
    public void createParkingSlot(int slots) {
        for (int i = 1; i <= slots; i++) {
            Vehicle vehicle = new Vehicle();
            vehicle.setSlotNo(i);
            vehicle.setOccupied(false);
            parkingRepository.save(vehicle);
        }
        System.out.println("Created a parking lot with " + slots + " slots");
    }
}
