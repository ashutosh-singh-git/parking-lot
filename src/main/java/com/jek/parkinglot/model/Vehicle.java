package com.jek.parkinglot.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vehicle {

    @Id
    private int slotNo;
    private String registrationNo;
    private String color;
    private boolean occupied;

    public int getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(int slotNo) {
        this.slotNo = slotNo;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    @Override
    public String toString() {
        return slotNo + "           " + registrationNo + "      " + color;
    }
}
