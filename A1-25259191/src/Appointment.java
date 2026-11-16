/**
 * An immutable appointment for the current day.
 * The manager checks clinic slots and conflicts; this class checks its own data.
 */
public class Appointment implements Comparable<Appointment> {
    private final Patient patient;
    private final int timeInMinutes;
    private final HealthProfessional healthProfessional;

    /**
     * Creates a complete appointment.
     * @param patient patient attending the appointment
     * @param timeInMinutes minutes since midnight, from 0 to 1439
     * @param healthProfessional chosen professional of any supported type
     * @throws IllegalArgumentException if a required object or time is invalid
     */
    public Appointment(Patient patient, int timeInMinutes,
            HealthProfessional healthProfessional) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient is required.");
        }
        if (healthProfessional == null) {
            throw new IllegalArgumentException("Health professional is required.");
        }
        if (timeInMinutes < 0 || timeInMinutes >= 24 * 60) {
            throw new IllegalArgumentException("Time must be between 00:00 and 23:59.");
        }
        this.patient = patient;
        this.timeInMinutes = timeInMinutes;
        this.healthProfessional = healthProfessional;
    }

    /**
     * Provides the immutable patient details.
     * @return the patient associated with this appointment
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Provides the numeric start time used for ordering and matching.
     * @return appointment start time as minutes since midnight
     */
    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    /**
     * Provides the chosen professional through the shared base type.
     * @return the chosen professional
     */
    public HealthProfessional getHealthProfessional() {
        return healthProfessional;
    }

    /**
     * Provides a readable start time.
     * @return the start time in HH:mm form
     */
    public String getTimeLabel() {
        return formatTime(timeInMinutes);
    }

    /**
     * Parses a 24-hour time without tying it to any clinic's configured slots.
     * @param timeLabel a time in HH:mm form
     * @return minutes since midnight
     * @throws IllegalArgumentException if the input is not a valid HH:mm time
     */
    public static int parseTime(String timeLabel) {
        if (timeLabel == null) {
            throw new IllegalArgumentException("Time is required in HH:mm form.");
        }
        String time = timeLabel.trim();
        if (time.length() != 5 || time.charAt(2) != ':') {
            throw new IllegalArgumentException("Time must use HH:mm form, for example 08:30.");
        }
        for (int i = 0; i < time.length(); i++) {
            if (i != 2 && (time.charAt(i) < '0' || time.charAt(i) > '9')) {
                throw new IllegalArgumentException("Time must contain numeric hours and minutes.");
            }
        }
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));
        if (hour > 23 || minute > 59) {
            throw new IllegalArgumentException("Time must be between 00:00 and 23:59.");
        }
        return hour * 60 + minute;
    }

    /**
     * Formats a valid time without relying on string ordering for comparisons.
     * @param minutes minutes since midnight
     * @return a two-digit hour and two-digit minute
     * @throws IllegalArgumentException if minutes fall outside one day
     */
    public static String formatTime(int minutes) {
        if (minutes < 0 || minutes >= 24 * 60) {
            throw new IllegalArgumentException("Time must be between 00:00 and 23:59.");
        }
        int hour = minutes / 60;
        int minute = minutes % 60;
        return (hour < 10 ? "0" : "") + hour + ":"
                + (minute < 10 ? "0" : "") + minute;
    }

    /**
     * Orders appointments by start time; simultaneous appointments compare equally.
     * @param other the appointment to compare with
     * @return a negative, zero or positive value according to time order
     */
    @Override
    public int compareTo(Appointment other) {
        return timeInMinutes - other.timeInMinutes;
    }

    /** @return patient, time and full details of the chosen professional */
    @Override
    public String toString() {
        return "Time: " + getTimeLabel() + " | " + patient
                + System.lineSeparator() + "  Professional: " + healthProfessional;
    }
}
