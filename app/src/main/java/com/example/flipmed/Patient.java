package com.example.flipmed;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Patient extends FlipMed {

    Scanner sc = new Scanner(System.in);
    @Override
    void register() {
        System.out.println("Write your name");
        String name = sc.next();
        patientAppointmentMap.put(name,"");
    }

    ArrayList<String> viewAppointments() {

        return viewAppointments(ViewerType.PATIENT);

    }


    public void bookSlot() {

        int id = generateId();

        System.out.println("Select on basis of speciality");

        String specialityRequired = SPECIALITY.CARDIOLOGIST.name();

        HashMap<String, String> allDoctorsWithRequiredSpeciality = availableSlotsMap.get(specialityRequired);

        assert allDoctorsWithRequiredSpeciality != null;
        ArrayList<String> availableSpecialistsName = (ArrayList<String>) allDoctorsWithRequiredSpeciality.keySet();

        System.out.println("With whome you want to fix appointment");

        String selectedSpecialist = fixAppointmentWith(availableSpecialistsName);

        String[] availableSlots = allDoctorsWithRequiredSpeciality.get(selectedSpecialist).split(",");

        System.out.println("Select slot");

        String selectedSlot = availableSlots[0]; //Hardcoded 0th slot

        String consultationInfo = specialityRequired + "-" + selectedSpecialist + "-" + getNameFromLocal() + "-" + selectedSlot;

        //Setting the record of Consultation against the id
        appointment.put(id, consultationInfo);

        //Sharing same ids into the patient and Doctor map against their name
        shareAppointmentId(id, selectedSpecialist);

        //Updating the available slots
        updateAvailableSlotsMap(consultationInfo);

    }


    public void cancelSlot() {

        String[] patientAppointmentIds = patientAppointmentMap.get(getNameFromLocal()).split("-");

        String idToRemove = getAppointmentToCancel(patientAppointmentIds);

        String[] detailsOfAppointment = appointment.get(idToRemove).split("-");

        String[] doctorAppointmentIds = doctorAppointmentMap.get(detailsOfAppointment[1]).split("-");


        //Remove the record from the appointment's map
        appointment.remove(idToRemove);

        //Update the doctor's map
        String updatedAppointmentsForDoctor = updatedAppointments(doctorAppointmentIds, idToRemove);
        doctorAppointmentMap.put(detailsOfAppointment[1], updatedAppointmentsForDoctor);

        //Update the patient's map
        String updatedAppointmentsForPatient = updatedAppointments(patientAppointmentIds, idToRemove);
        patientAppointmentMap.put(getNameFromLocal(), updatedAppointmentsForPatient);

        //Update the main source for slot booking
        HashMap<String, String> specialists = availableSlotsMap.get(detailsOfAppointment[0]);
        String slots = specialists.get(detailsOfAppointment[1]);

        //Free the slot
        freeUpTheSlot(slots, detailsOfAppointment[3], specialists, detailsOfAppointment[1]);

    }


    private String updatedAppointments(String[] ids, String idToRemove) {
        StringBuilder sb = new StringBuilder();

        for (String id : ids) {
            if (id.equals(idToRemove)) {
                continue;
            } else {
                sb.append(id + "-");
            }
        }
        return sb.toString();
    }

    private String getAppointmentToCancel(String[] appointmentIds) {
        //Will return the appointment to cancel
        ArrayList<String> appointmentDetails = new ArrayList<>();
        for (String id : appointmentIds) {
            appointmentDetails.add(appointment.get(id));
        }

        return appointmentToCancel(appointmentDetails);
    }

    //Returns the id to cancel
    private String appointmentToCancel(ArrayList<String> appointmentDetails) {
        return "";
    }

    //This function saves the same id to the patient and the doctor's map
    private void shareAppointmentId(int id, String doctorName) {

        //Adds the id to the doctor's map
        if (doctorAppointmentMap.containsKey(doctorName)) {
            doctorAppointmentMap.put(doctorName, doctorAppointmentMap.get(doctorName) + "-" + id);
        } else {
            doctorAppointmentMap.put(doctorName, String.valueOf(id));
        }

        //Adds the id to patient's map
        if (patientAppointmentMap.containsKey(getNameFromLocal())) {
            patientAppointmentMap.put(getNameFromLocal(), patientAppointmentMap.get(getNameFromLocal()) + "-" + id);
        } else {
            patientAppointmentMap.put(getNameFromLocal(), String.valueOf(id));
        }

    }


    //This function returns the name of the doctor, user want to get appointment fixed
    private String fixAppointmentWith(ArrayList<String> availableSpecialistsName) {
        return "";
    }

    private int generateId() {
        return -1;
    }


}

