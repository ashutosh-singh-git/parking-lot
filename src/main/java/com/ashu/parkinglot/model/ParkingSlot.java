package com.ashu.parkinglot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"registrationNo"})})
public class ParkingSlot {

    @Id
    private int slotNo;
    @Column(name = "registrationNo")
    private String registrationNo;
    private String color;
    private boolean occupied;

    public int getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(int slotNo) {
        this.slotNo = slotNo;
    }

    public ParkingSlot() {
    }

    public ParkingSlot(int slotNo) {
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
        return slotNo + "           " + registrationNo + "       " + color;
    }
}
