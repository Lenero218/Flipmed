import java.util.ArrayList;
import java.util.HashMap;


enum ViewerType {
    DOCTOR,
    PATIENT
}

abstract class FlipMed {
    /*
     * Map of all the Doctors segregated through their speciality with the slots of their availability

     * Speciality against the map of Doctors with Slots of availability

     * Key -> Cardiologist
     * Value -> Map<name_of_doctor,availability_Slots>

     */
    HashMap<String, HashMap<String, String>> availableSlotsMap;

    /*
        Stores appointment_id as key and string which represents appointment of Patient and Doctor - in format of
        SPECIALITY - DOCTOR_NAME - PATIENT_NAME - SLOT as value
    */
    HashMap<Integer, String> appointment;

    /*
     * Stores the appointment id's with different doctors against the name of the patient
     */
    HashMap<String, String> patientAppointmentMap;

    /*
     * Stores the appointment id's with patients against the name of the Doctor
     */
    HashMap<String, String> doctorAppointmentMap;


    public FlipMed() {
        availableSlotsMap = new HashMap<>();
        appointment = new HashMap<>();
        patientAppointmentMap = new HashMap<>();
        doctorAppointmentMap = new HashMap<>();
    }

    abstract void register();

    public ArrayList<String> viewAppointments(ViewerType type) {

        ArrayList<Integer> appointmentIds = getAppointmentIds();

        ArrayList<String> appointments = new ArrayList<>();

        for (int id : appointmentIds) {
            String appointmentDetails = this.appointment.get(id);

            assert appointmentDetails != null;

            String[] details = appointmentDetails.split("-");

            //Since the value at 2nd index represent patient name and 1st index represent Doctor
            switch (type) {
                case DOCTOR:
                    appointments.add(details[2]);
                case PATIENT:
                    appointments.add(details[1]);
            }


        }
        return appointments;
    }

    public ArrayList<Integer> getAppointmentIds() {
        return getAppointmentIdsFromLocal();
    }

    public void freeUpTheSlot(String slots, String slot, HashMap<String, String> specialists, String docName) {
        String updatedSlots = slots + "," + slot;
        specialists.put(docName, updatedSlots);
    }

    public String getNameFromLocal() {
        return "";
    }

    public void updateAvailableSlotsMap(String info) {
        String[] details = info.split("-");

        HashMap<String, String> availableDoctors = availableSlotsMap.get(details[0]);

        String[] getSlots = availableDoctors.get(details[1]).split(",");

        //Create new string of slots accept the selected one and update the source map

        StringBuilder sb = new StringBuilder();

        for (String slot : getSlots) {
            if (slot.equals(details[3])) {
                continue;
            } else {
                sb.append(slot);
            }
        }

        String newSlots = sb.toString();

        //Updated the slots with the new slots of selected doctor
        availableDoctors.put(details[1], newSlots);

    }


    private ArrayList<Integer> getAppointmentIdsFromLocal() {
        //This will return the ids from the local db, or from the preferences
        return new ArrayList<>();
    }

}
