package com.ashu.parkinglot.service;

public interface ParkingService {

    void parkVehicle(String regNo, String color);

    void getParkingStatus();

    void freeParkingLot(int slot);

    void createParkingSlot(int slots);

    void getRegNoForColour(String colour);

    void getSlotNoForColour(String colour);

    void getSlotNoForRegNo(String regNo);
}
