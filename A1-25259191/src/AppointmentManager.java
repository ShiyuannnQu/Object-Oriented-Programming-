import java.util.ArrayList;
import java.util.Collections;

/** Manages one current day's appointments and a centrally configured slot table. */
public class AppointmentManager {
    private final ArrayList<Appointment> appointments;
    private final int[] allowedTimeSlots;

    /** Creates the default half-hour schedule, including starts at 08:00 and 16:00. */
    public AppointmentManager() {
        this(defaultTimeSlots());
    }

    /**
     * Creates a manager with a replacement daily schedule.
     * @param allowedTimeSlots valid, strictly increasing times in minutes since midnight
     * @throws IllegalArgumentException if the table is empty, unordered or invalid
     */
    public AppointmentManager(int[] allowedTimeSlots) {
        if (allowedTimeSlots == null || allowedTimeSlots.length == 0) {
            throw new IllegalArgumentException("At least one clinic time slot is required.");
        }
        this.allowedTimeSlots = new int[allowedTimeSlots.length];
        for (int i = 0; i < allowedTimeSlots.length; i++) {
            int slot = allowedTimeSlots[i];
            if (slot < 0 || slot >= 24 * 60) {
                throw new IllegalArgumentException("Clinic slots must be valid times within one day.");
            }
            if (i > 0 && slot <= allowedTimeSlots[i - 1]) {
                throw new IllegalArgumentException("Clinic slots must be unique and strictly increasing.");
            }
            this.allowedTimeSlots[i] = slot;
        }
        appointments = new ArrayList<Appointment>();
    }

    private static int[] defaultTimeSlots() {
        int openingTime = 8 * 60;
        int finalStartTime = 16 * 60;
        int interval = 30;
        int[] slots = new int[(finalStartTime - openingTime) / interval + 1];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = openingTime + i * interval;
        }
        return slots;
    }

    /**
     * Provides the configured schedule without exposing the internal array.
     * @return a defensive copy of the configured daily start times
     */
    public int[] getAllowedTimeSlots() {
        int[] copy = new int[allowedTimeSlots.length];
        for (int i = 0; i < allowedTimeSlots.length; i++) {
            copy[i] = allowedTimeSlots[i];
        }
        return copy;
    }

    /**
     * Counts the appointments currently stored.
     * @return the current number of appointments
     */
    public int getAppointmentCount() {
        return appointments.size();
    }

    /**
     * Validates and creates a booking from raw patient and time information.
     * @param patientName patient's name
     * @param mobilePhone patient's numeric contact number, preserved as text
     * @param time appointment start in HH:mm form
     * @param professional chosen professional of any subtype
     * @return a success message or a warning; invalid data never changes the list
     */
    public String addAppointment(String patientName, String mobilePhone,
            String time, HealthProfessional professional) {
        try {
            Patient patient = new Patient(patientName, mobilePhone);
            Appointment appointment = new Appointment(patient, Appointment.parseTime(time), professional);
            return addAppointment(appointment);
        } catch (IllegalArgumentException exception) {
            return "WARNING: " + exception.getMessage();
        }
    }

    /**
     * Adds a complete appointment after checking its slot and professional identity.
     * @param appointment a complete appointment
     * @return a success message or a warning explaining why no booking was added
     */
    public String addAppointment(Appointment appointment) {
        if (appointment == null) {
            return "WARNING: Appointment is required.";
        }
        boolean permittedSlot = false;
        for (int slot : allowedTimeSlots) {
            if (slot == appointment.getTimeInMinutes()) {
                permittedSlot = true;
                break;
            }
        }
        if (!permittedSlot) {
            return "WARNING: " + appointment.getTimeLabel() + " is not a configured clinic slot.";
        }
        for (Appointment existing : appointments) {
            if (existing.getHealthProfessional().equals(appointment.getHealthProfessional())
                    && existing.getTimeInMinutes() == appointment.getTimeInMinutes()) {
                return "WARNING: Professional ID " + appointment.getHealthProfessional().getId()
                        + " is already booked at " + appointment.getTimeLabel() + ".";
            }
        }
        appointments.add(appointment);
        return "SUCCESS: Booked " + appointment.getPatient().getName() + " at "
                + appointment.getTimeLabel() + " with professional ID "
                + appointment.getHealthProfessional().getId() + ".";
    }

    /**
     * Provides the complete collection without exposing the internal list.
     * @return a copy of all appointments in insertion order
     */
    public ArrayList<Appointment> getAppointments() {
        return new ArrayList<Appointment>(appointments);
    }

    /**
     * Filters without exposing the manager's internal collection.
     * @param professionalId the requested professional's numeric ID
     * @return all matching appointments, or an empty list
     */
    public ArrayList<Appointment> getAppointments(int professionalId) {
        ArrayList<Appointment> matches = new ArrayList<Appointment>();
        for (Appointment appointment : appointments) {
            if (appointment.getHealthProfessional().getId() == professionalId) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    /**
     * Filters by the patient's contact number.
     * @param mobilePhone patient contact number
     * @return all matching appointments, or an empty list
     */
    public ArrayList<Appointment> getAppointments(String mobilePhone) {
        ArrayList<Appointment> matches = new ArrayList<Appointment>();
        if (mobilePhone == null) {
            return matches;
        }
        String phone = mobilePhone.trim();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getMobilePhone().equals(phone)) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    /**
     * Sorts a copy of the complete collection.
     * @return all appointments ordered from earliest to latest
     */
    public ArrayList<Appointment> getSortedAppointments() {
        ArrayList<Appointment> sorted = getAppointments();
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Filters by professional and sorts the result independently.
     * @param professionalId professional whose appointments are requested
     * @return a time-ordered copy of that professional's appointments
     */
    public ArrayList<Appointment> getSortedAppointments(int professionalId) {
        ArrayList<Appointment> sorted = getAppointments(professionalId);
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Filters by patient contact and sorts the result independently.
     * @param mobilePhone patient whose appointments are requested
     * @return a time-ordered copy of that patient's appointments
     */
    public ArrayList<Appointment> getSortedAppointments(String mobilePhone) {
        ArrayList<Appointment> sorted = getAppointments(mobilePhone);
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Cancels the uniquely identified professional/time booking.
     * @param professionalId chosen professional's ID
     * @param timeInMinutes booked start time
     * @return success or a warning if no matching appointment exists
     */
    public String cancelAppointment(int professionalId, int timeInMinutes) {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getHealthProfessional().getId() == professionalId
                    && appointment.getTimeInMinutes() == timeInMinutes) {
                appointments.remove(i);
                return "SUCCESS: Cancelled professional ID " + professionalId
                        + " at " + appointment.getTimeLabel() + ".";
            }
        }
        return "WARNING: No appointment matches that professional ID and time.";
    }

    /**
     * Cancels every appointment for a contact number, including consecutive matches.
     * @param mobilePhone contact number identifying the patient's bookings
     * @return the number cancelled, or a warning if none matched
     */
    public String cancelAppointment(String mobilePhone) {
        if (mobilePhone == null || mobilePhone.trim().length() == 0) {
            return "WARNING: A patient phone number is required for cancellation.";
        }
        int cancelled = 0;
        String phone = mobilePhone.trim();
        // Reverse traversal keeps remaining indexes valid after removal.
        for (int i = appointments.size() - 1; i >= 0; i--) {
            if (appointments.get(i).getPatient().getMobilePhone().equals(phone)) {
                appointments.remove(i);
                cancelled++;
            }
        }
        if (cancelled == 0) {
            return "WARNING: No appointments match patient phone " + phone + ".";
        }
        return "SUCCESS: Cancelled " + cancelled + " appointment(s) for patient phone " + phone + ".";
    }

    /**
     * Converts a result list to readable console text, including an empty-result warning.
     * @param selected the complete or filtered list to display
     * @return numbered full details, or a warning if there is nothing to show
     */
    public String formatAppointments(ArrayList<Appointment> selected) {
        if (selected == null || selected.isEmpty()) {
            return "WARNING: No appointments found.";
        }
        String result = "";
        for (int i = 0; i < selected.size(); i++) {
            result += (i + 1) + ". " + selected.get(i) + System.lineSeparator();
        }
        return result;
    }
}
