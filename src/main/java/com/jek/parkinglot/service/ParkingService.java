package com.jek.parkinglot.service;

public interface ParkingService {

    void parkVehicle(String regNo, String color);

    void getParkingStatus();

    void freeParkingLot(int slot);

    void createParkingSlot(int slots);
}
