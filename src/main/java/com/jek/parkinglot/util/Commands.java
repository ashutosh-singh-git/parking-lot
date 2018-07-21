package com.jek.parkinglot.util;

import java.util.HashSet;
import java.util.Set;

public class Commands {

    public static final String CREATE = "create_parking_lot";
    public static final String PARK = "park";
    public static final String LEAVE = "leave";
    public static final String STATUS = "status";
    public static final String REG_NO_WITH_COLOUR = "registration_numbers_for_cars_with_colour";
    public static final String SLOT_NO_WITH_COLOUR = "slot_numbers_for_cars_with_colour";
    public static final String SLOT_NO_FOR_REG_NO = "slot_number_for_registration_number";
    public static final String EXIT = "exit";

    public static final Set<String> commandsSet = new HashSet<String>() {{
        add(CREATE);
        add(PARK);
        add(LEAVE);
        add(STATUS);
        add(REG_NO_WITH_COLOUR);
        add(SLOT_NO_WITH_COLOUR);
        add(SLOT_NO_FOR_REG_NO);
        add(EXIT);
    }};
}
