package com.jek.parkinglot.service;

import com.jek.parkinglot.Application;
import com.jek.parkinglot.model.ParkingSlot;
import com.jek.parkinglot.repository.ParkingRepository;
import com.jek.parkinglot.service.impl.ParkingServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class,
        initializers = ConfigFileApplicationContextInitializer.class)
public class ParkingServiceTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public ParkingService employeeService() {
            return new ParkingServiceImpl();
        }
    }

    @Autowired
    private ParkingService parkingService;

    @MockBean
    private ParkingRepository parkingRepository;

    private PrintStream sysOut;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private ParkingSlot unavailableSlot;
    private ParkingSlot availableSlot;


    @Before
    public void setUpStreams() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
        MockitoAnnotations.initMocks(this);
        unavailableSlot = new ParkingSlot();
        unavailableSlot.setSlotNo(1);
        unavailableSlot.setRegistrationNo("KA-4829");
        unavailableSlot.setColor("BLUE");
        unavailableSlot.setOccupied(true);
        availableSlot = new ParkingSlot(1);
        availableSlot.setOccupied(false);
    }

    @After
    public void revertStreams() {
        System.setOut(sysOut);
    }

    @Test
    public void createParkingLot() {
        int slots = 3;
        parkingService.createParkingSlot(slots);
        Assert.assertEquals("Created a parking lot with " + slots + " slots", outContent.toString().trim());
    }

    @Test
    public void parkVehicleSuccess() {
        int slot = 1;
        when(parkingRepository.findAll()).thenReturn(Collections.singletonList(new ParkingSlot(slot)));
        when(parkingRepository.save(unavailableSlot)).thenReturn(unavailableSlot);
        parkingService.parkVehicle(unavailableSlot.getRegistrationNo(), unavailableSlot.getColor());
        Assert.assertEquals("Allocated slot number: " + slot, outContent.toString().trim());
    }

    @Test
    public void parkingLotFull() {
        when(parkingRepository.findAll()).thenReturn(Collections.singletonList(unavailableSlot));
        when(parkingRepository.save(unavailableSlot)).thenReturn(unavailableSlot);
        parkingService.parkVehicle(unavailableSlot.getRegistrationNo(), unavailableSlot.getColor());
        Assert.assertEquals("Sorry, parking lot is full", outContent.toString().trim());
    }

    @Test
    public void parkVehicleNoParkingLot() {
        when(parkingRepository.findAll()).thenReturn(new ArrayList<>());
        when(parkingRepository.save(unavailableSlot)).thenReturn(unavailableSlot);
        parkingService.parkVehicle(unavailableSlot.getRegistrationNo(), unavailableSlot.getColor());
        Assert.assertEquals("Please create a parking lot first", outContent.toString().trim());
    }

    @Test
    public void getParkingStatusAllSlotsAvailable() {
        when(parkingRepository.findAllByOccupiedTrue()).thenReturn(new ArrayList<>());
        parkingService.getParkingStatus();
        Assert.assertEquals("All parking slots are available", outContent.toString().trim());
    }

    @Test
    public void getParkingStatusSlotsOccupied() {
        when(parkingRepository.findAllByOccupiedTrue()).thenReturn(Collections.singletonList(unavailableSlot));
        parkingService.getParkingStatus();
        Assert.assertEquals("Slot No.    Registration No.    Colour\n" +
                "1           KA-4829       BLUE", outContent.toString().trim());
    }

    @Test
    public void freeParkingLotSlotIsFree() {
        when(parkingRepository.findBySlotNo(unavailableSlot.getSlotNo())).thenReturn(unavailableSlot);
        parkingService.freeParkingLot(unavailableSlot.getSlotNo());
        Assert.assertEquals("Slot number " + unavailableSlot.getSlotNo() + " is free", outContent.toString().trim());
    }

    @Test
    public void freeParkingLotSlotIsAlreadyFree() {
        when(parkingRepository.findBySlotNo(availableSlot.getSlotNo())).thenReturn(availableSlot);
        parkingService.freeParkingLot(availableSlot.getSlotNo());
        Assert.assertEquals("Slot number " + availableSlot.getSlotNo() + " is already free", outContent.toString().trim());
    }

    @Test
    public void freeParkingLotInvalidSlot() {
        when(parkingRepository.findBySlotNo(availableSlot.getSlotNo())).thenReturn(null);
        parkingService.freeParkingLot(availableSlot.getSlotNo());
        Assert.assertEquals("Invalid slot no " + availableSlot.getSlotNo(), outContent.toString().trim());
    }

    @Test
    public void getRegNoForColorSuccess() {
        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setOccupied(true);
        parkingSlot.setRegistrationNo("JKS");
        parkingSlot.setColor(unavailableSlot.getColor());
        when(parkingRepository.findAllByOccupiedTrueAndColor(unavailableSlot.getColor())).thenReturn(Arrays.asList(unavailableSlot, parkingSlot));
        parkingService.getRegNoForColour(unavailableSlot.getColor());
        Assert.assertEquals(unavailableSlot.getRegistrationNo() + ", " + parkingSlot.getRegistrationNo(), outContent.toString().trim());
    }

    @Test
    public void getRegNoForColorNotFound() {
        when(parkingRepository.findAllByOccupiedTrueAndColor(unavailableSlot.getColor())).thenReturn(new ArrayList<>());
        parkingService.getRegNoForColour(unavailableSlot.getColor());
        Assert.assertEquals("Not found", outContent.toString().trim());
    }

    @Test
    public void getSlotNoForColourSuccess() {
        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setOccupied(true);
        parkingSlot.setSlotNo(2);
        parkingSlot.setColor(unavailableSlot.getColor());
        when(parkingRepository.findAllByOccupiedTrueAndColor(unavailableSlot.getColor())).thenReturn(Arrays.asList(unavailableSlot, parkingSlot));
        parkingService.getSlotNoForColour(unavailableSlot.getColor());
        Assert.assertEquals(unavailableSlot.getSlotNo() + ", " + parkingSlot.getSlotNo(), outContent.toString().trim());
    }

    @Test
    public void getSlotNoForRegNoSuccess() {
        when(parkingRepository.findByOccupiedTrueAndRegistrationNo(unavailableSlot.getRegistrationNo())).thenReturn(unavailableSlot);
        parkingService.getSlotNoForRegNo(unavailableSlot.getRegistrationNo());
        Assert.assertEquals(unavailableSlot.getSlotNo()+"", outContent.toString().trim());
    }

    @Test
    public void getSlotNoForRegNoNotFound() {
        when(parkingRepository.findByOccupiedTrueAndRegistrationNo(unavailableSlot.getRegistrationNo())).thenReturn(null);
        parkingService.getSlotNoForRegNo(unavailableSlot.getRegistrationNo());
        Assert.assertEquals("Not found", outContent.toString().trim());
    }
}
