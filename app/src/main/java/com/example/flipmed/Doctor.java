package com.example.flipmed;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

enum SPECIALITY {
    CARDIOLOGIST,
    DERMATOLOGIST,
    GENERAL_PHYSICIAN,
    ORTHOPEDIC
}

public class Doctor extends FlipMed {


    Scanner sc = new Scanner(System.in);

    @Override
    void register() {
        System.out.println("Type your name here");
        String name = "";

        ArrayList<String> slots = new ArrayList<>();
        ;

        System.out.println("Select Speciality");

        SPECIALITY speciality = SPECIALITY.ORTHOPEDIC; //By default

        System.out.println("Do you want to add availability now, type y or n");

        String vote = sc.next();

        if (vote.equals("y")) {
            slots = addAvailability();
        } else {
            slots = new ArrayList<>();
        }

        String slotsString = String.join(", ", slots);

        HashMap<String, String> specializedDoctor = new HashMap<>();

        specializedDoctor.put(name, slotsString);

        availableSlotsMap.put(speciality.name(), specializedDoctor);

    }

    ArrayList<String> viewAppointments() {

        return viewAppointments(ViewerType.DOCTOR);

    }


    //Register
    private ArrayList<String> addAvailability() {

        ArrayList<String> slots = new ArrayList<>();


        System.out.println("Write available slot in format of 00:00");

        String slot = sc.next();

        if (!slot.substring(3).equals("00")) {
            System.out.println("Invalid slot, Please fill in format of 00:00 - 00:00");
        }

        while (!slot.substring(3).equals("00")) {
            System.out.println("Please rewrite the slot in correct format");
            slot = sc.next();
        }

        slots.add(slot);

        return slots;
    }
}

