import java.util.ArrayList;

public class AppointmentSystemChecks {
    private static int passedChecks;

    public static void main(String[] args) {
        checkHealthProfessionals();
        checkPatients();
        checkAppointmentsAndTime();
        checkBookingRules();
        checkFilteringSortingAndCopies();
        checkCancellation();
        checkCustomSchedules();
        System.out.println("All " + passedChecks + " checks passed.");
    }

    private static void checkHealthProfessionals() {
        String[] suppliedDays = {"Monday", "Wednesday", "Friday"};
        HealthProfessional professional = new GeneralPractitioner(101, "Dr Ada Chen", suppliedDays);
        check(professional.getId() == 101, "A professional retains the supplied ID");
        check("Dr Ada Chen".equals(professional.getName()), "A professional retains the supplied name");
        check(professional.worksOn("Monday"), "A professional works on a recorded day");
        check(!professional.worksOn("Tuesday"), "A professional does not work on an unrecorded day");
        check(professional.getWorkingDays().length == 3, "All supplied working days are retained");

        suppliedDays[0] = "Tuesday";
        check(professional.worksOn("Monday") && !professional.worksOn("Tuesday"),
                "Changing the constructor array cannot change a professional's working days");
        String[] returnedDays = professional.getWorkingDays();
        returnedDays[0] = "Tuesday";
        check(professional.worksOn("Monday") && !professional.worksOn("Tuesday"),
                "Changing a returned working-days array cannot change the professional");

        HealthProfessional sameId = new GeneralPractitioner(101, "Another representation", standardDays());
        HealthProfessional differentId = new GeneralPractitioner(102, "Dr Ada Chen", standardDays());
        check(professional.equals(professional), "Professional equality is reflexive");
        check(professional.equals(sameId), "Separate objects with the same professional ID are equal");
        check(sameId.equals(professional), "Professional equality is symmetric");
        check(professional.hashCode() == sameId.hashCode(), "Equal professionals have equal hash codes");
        check(!professional.equals(differentId), "Different professional IDs are not equal");
        check(!professional.equals(null), "A professional is not equal to null");
        check(!professional.equals("101"), "A professional is not equal to an unrelated object");
        String details = professional.toString();
        check(details.contains("101") && details.contains("Dr Ada Chen") && details.contains("Monday"),
                "Professional details include identity, name and working days");

        HealthProfessional[] professionals = sixProfessionals();
        for (int index = 0; index < professionals.length; index++) {
            HealthProfessional current = professionals[index];
            check(current.getId() == 701 + index, "Each concrete type can be read through the base type");
            check(current.toString().contains(current.getName()),
                    "Each concrete type presents its details through the base type");
        }

        expectInvalidProfessional(-1, "Dr Valid", standardDays(), "Negative professional ID");
        expectInvalidProfessional(103, null, standardDays(), "Null professional name");
        expectInvalidProfessional(103, "   ", standardDays(), "Blank professional name");
        expectInvalidProfessional(103, "Dr Valid", null, "Null working-days array");
        expectInvalidProfessional(103, "Dr Valid", new String[0], "Empty working-days array");
        expectInvalidProfessional(103, "Dr Valid", new String[] {null}, "Null working-day entry");
        expectInvalidProfessional(103, "Dr Valid", new String[] {"Funday"}, "Unknown working day");
    }

    private static void checkPatients() {
        Patient patient = new Patient("Alice Green", "0412345678");
        check("0412345678".equals(patient.getMobilePhone()), "A patient's phone retains its leading zero");
        expectInvalidPatient(null, "0412345678", "Null patient name");
        expectInvalidPatient("   ", "0412345678", "Blank patient name");
        expectInvalidPatient("Alice Green", null, "Null patient phone");
        expectInvalidPatient("Alice Green", "   ", "Blank patient phone");
        expectInvalidPatient("Alice Green", "04ABC45678", "Non-numeric patient phone");
    }

    private static void checkAppointmentsAndTime() {
        Patient patient = new Patient("Alice Green", "0412345678");
        HealthProfessional professional = new GeneralPractitioner(201, "Dr Time", standardDays());
        Appointment first = new Appointment(patient, 480, professional);
        Appointment later = new Appointment(patient, 600, professional);
        check(first.getPatient() == patient, "An appointment refers to its supplied patient");
        check(first.getHealthProfessional() == professional, "An appointment refers to its supplied professional");
        check(first.getTimeInMinutes() == 480, "An appointment retains its time in minutes");
        check("08:00".equals(first.getTimeLabel()), "An appointment displays a zero-padded time");
        check(first.compareTo(later) < 0, "Earlier appointments compare before later appointments");
        check(later.compareTo(first) > 0, "Later appointments compare after earlier appointments");
        check(first.compareTo(first) == 0, "An appointment compares equally to itself");
        String details = first.toString();
        check(details.contains("Alice Green") && details.contains("0412345678"),
                "Appointment details include patient name and phone");
        check(details.contains("Dr Time") && details.contains("201") && details.contains("Monday"),
                "Appointment details include the chosen professional's information");
        check(details.contains("08:00"), "Appointment details include appointment time");
        check(new Appointment(patient, 0, professional).getTimeInMinutes() == 0,
                "An individual appointment can represent the beginning of a day");
        check(new Appointment(patient, 1439, professional).getTimeInMinutes() == 1439,
                "An individual appointment can represent the last minute of a day");

        check(Appointment.parseTime("00:00") == 0, "00:00 parses to zero minutes");
        check(Appointment.parseTime("08:00") == 480, "08:00 parses to 480 minutes");
        check(Appointment.parseTime("16:00") == 960, "16:00 parses to 960 minutes");
        check(Appointment.parseTime("23:59") == 1439, "23:59 parses to the last minute");
        check("00:00".equals(Appointment.formatTime(0)), "Zero minutes formats as 00:00");
        check("08:00".equals(Appointment.formatTime(480)), "480 minutes formats as 08:00");
        check("23:59".equals(Appointment.formatTime(1439)), "The last minute formats as 23:59");

        expectInvalidAppointment(null, 480, professional, "Null appointment patient");
        expectInvalidAppointment(patient, 480, null, "Null appointment professional");
        expectInvalidAppointment(patient, -1, professional, "Negative appointment time");
        expectInvalidAppointment(patient, 1440, professional, "Appointment time outside the current day");
        expectInvalidTime(null);
        expectInvalidTime("");
        expectInvalidTime("8:00");
        expectInvalidTime("08:0");
        expectInvalidTime("08-00");
        expectInvalidTime("08:00:00");
        expectInvalidTime("24:00");
        expectInvalidTime("08:60");
        expectInvalidFormattedTime(-1);
        expectInvalidFormattedTime(1440);
    }

    private static void checkBookingRules() {
        AppointmentManager manager = new AppointmentManager();
        Patient patient = new Patient("Booking Patient", "0412345678");
        HealthProfessional professional = new GeneralPractitioner(301, "Dr Booking", standardDays());
        HealthProfessional anotherProfessional = new Nurse(302, "Nurse Booking", standardDays());
        check(manager.getAppointmentCount() == 0, "A new manager starts empty");
        int[] slots = manager.getAllowedTimeSlots();
        check(slots.length == 17, "The inclusive default schedule contains 17 half-hour slots");
        check(slots[0] == 480 && slots[slots.length - 1] == 960,
                "The default schedule includes both 08:00 and 16:00");
        slots[0] = 0;
        check(manager.getAllowedTimeSlots()[0] == 480, "The default schedule cannot be changed through its getter");

        checkSuccess(manager.addAppointment(new Appointment(patient, 480, professional)), "Book the first default slot");
        checkSuccess(manager.addAppointment(new Appointment(patient, 480, anotherProfessional)),
                "Different professionals can use the same slot, including for the same patient");
        check(manager.getAppointmentCount() == 2, "Both different-professional bookings are retained");
        checkWarning(manager.addAppointment(new Appointment(patient, 480, professional)),
                "Reject a repeat slot for the same professional object");
        check(manager.getAppointmentCount() == 2, "A repeated booking leaves the collection unchanged");
        HealthProfessional duplicateIdentity = new GeneralPractitioner(301, "Another object", standardDays());
        checkWarning(manager.addAppointment(new Appointment(patient, 480, duplicateIdentity)),
                "Reject a repeat slot represented by a different object with the same ID");
        check(manager.getAppointmentCount() == 2, "An ID-based conflict leaves the collection unchanged");
        checkSuccess(manager.addAppointment(new Appointment(patient, 960, professional)), "Book the inclusive 16:00 boundary");
        check(manager.getAppointmentCount() == 3, "A professional can have another appointment at a different time");

        checkWarning(manager.addAppointment(new Appointment(patient, 495, professional)), "Reject 08:15 outside the fixed slots");
        check(manager.getAppointmentCount() == 3, "A non-slot time leaves the collection unchanged");
        checkWarning(manager.addAppointment(new Appointment(patient, 990, professional)), "Reject 16:30 outside the clinic schedule");
        check(manager.getAppointmentCount() == 3, "An after-hours booking leaves the collection unchanged");
        checkWarning(manager.addAppointment((Appointment) null), "Reject a null appointment");
        check(manager.getAppointmentCount() == 3, "A null booking leaves the collection unchanged");
        checkWarning(manager.addAppointment("", "0412345678", "08:30", professional), "Reject an incomplete patient name");
        check(manager.getAppointmentCount() == 3, "An incomplete name leaves the collection unchanged");
        checkWarning(manager.addAppointment("Booking Patient", "04ABC45678", "08:30", professional), "Reject an invalid patient phone");
        check(manager.getAppointmentCount() == 3, "An invalid phone leaves the collection unchanged");
        checkWarning(manager.addAppointment("Booking Patient", "0412345678", "bad time", professional), "Report an invalid time string");
        check(manager.getAppointmentCount() == 3, "A malformed time leaves the collection unchanged");
        checkWarning(manager.addAppointment("Booking Patient", "0412345678", "08:30", null), "Reject a missing professional");
        check(manager.getAppointmentCount() == 3, "A missing professional leaves the collection unchanged");
        checkSuccess(manager.addAppointment("Booking Patient", "0412345678", "08:30", professional), "Book using the convenience overload");
        check(manager.getAppointmentCount() == 4, "The convenience overload retains the successful appointment");

        HealthProfessional[] professionals = sixProfessionals();
        for (int index = 0; index < professionals.length; index++) {
            checkSuccess(manager.addAppointment(new Appointment(patient, 540, professionals[index])),
                    "The manager accepts each concrete professional through the common base type");
        }
        check(manager.getAppointmentCount() == 10, "All six professional types can be booked");
        checkTimes(manager.getSortedAppointments(), new int[] {480, 480, 510, 540, 540, 540, 540, 540, 540, 960},
                "Equal-time bookings for different professionals sort without imposing a tie order");
    }

    private static void checkFilteringSortingAndCopies() {
        AppointmentManager manager = new AppointmentManager();
        HealthProfessional first = new GeneralPractitioner(401, "Dr First", standardDays());
        HealthProfessional second = new Nurse(402, "Nurse Second", standardDays());
        HealthProfessional third = new Paediatrician(403, "Dr Third", standardDays());
        String sharedPhone = "0412345678";
        checkSuccess(manager.addAppointment("Shared Patient", sharedPhone, "11:00", first), "Prepare unsorted booking 1");
        checkSuccess(manager.addAppointment("Other Patient", "0423456789", "08:30", first), "Prepare unsorted booking 2");
        checkSuccess(manager.addAppointment("Shared Patient", sharedPhone, "09:00", second), "Prepare unsorted booking 3");
        checkSuccess(manager.addAppointment("Third Patient", "0434567890", "08:00", third), "Prepare unsorted booking 4");
        checkSuccess(manager.addAppointment("Other Patient", "0423456789", "09:30", second), "Prepare unsorted booking 5");

        check(manager.getAppointments().size() == 5, "Retrieve the complete appointment collection");
        check(manager.getAppointments(401).size() == 2, "Filter appointments by professional ID");
        check(manager.getAppointments(sharedPhone).size() == 2, "Filter appointments by exact patient phone");
        check(manager.getAppointments(9999).isEmpty(), "An unknown professional has no matching appointments");
        check(manager.getAppointments("0499999999").isEmpty(), "An unknown phone has no matching appointments");

        ArrayList<Appointment> allCopy = manager.getAppointments();
        allCopy.clear();
        check(manager.getAppointmentCount() == 5, "Clearing the returned all-appointments list preserves internal data");
        ArrayList<Appointment> professionalCopy = manager.getAppointments(401);
        professionalCopy.remove(0);
        check(manager.getAppointments(401).size() == 2, "Changing a professional-filtered list preserves internal data");
        ArrayList<Appointment> patientCopy = manager.getAppointments(sharedPhone);
        patientCopy.clear();
        check(manager.getAppointments(sharedPhone).size() == 2, "Changing a patient-filtered list preserves internal data");

        checkTimes(manager.getSortedAppointments(), new int[] {480, 510, 540, 570, 660}, "Sort the complete collection by time");
        checkTimes(manager.getSortedAppointments(401), new int[] {510, 660}, "Sort a professional-filtered collection");
        checkTimes(manager.getSortedAppointments(sharedPhone), new int[] {540, 660}, "Sort a patient-filtered collection");
        check(manager.getAppointments().get(0).getTimeInMinutes() == 660,
                "Sorting a returned collection preserves the manager's original insertion order");
        check(manager.getAppointments(401).get(0).getTimeInMinutes() == 660,
                "Sorting a filtered result preserves its unsorted counterpart");
        check(manager.getSortedAppointments(9999).isEmpty(), "An unknown professional sorts to an empty result");
        check(manager.getSortedAppointments("0499999999").isEmpty(), "An unknown patient sorts to an empty result");
        ArrayList<Appointment> sortedCopy = manager.getSortedAppointments();
        sortedCopy.clear();
        check(manager.getAppointmentCount() == 5, "Changing the sorted complete list preserves internal data");
        ArrayList<Appointment> sortedProfessionalCopy = manager.getSortedAppointments(401);
        sortedProfessionalCopy.clear();
        check(manager.getAppointments(401).size() == 2, "Changing a sorted professional result preserves internal data");
        ArrayList<Appointment> sortedPatientCopy = manager.getSortedAppointments(sharedPhone);
        sortedPatientCopy.clear();
        check(manager.getAppointments(sharedPhone).size() == 2, "Changing a sorted patient result preserves internal data");
    }

    private static void checkCancellation() {
        AppointmentManager manager = new AppointmentManager();
        HealthProfessional first = new GeneralPractitioner(501, "Dr Cancellation", standardDays());
        HealthProfessional second = new Nurse(502, "Nurse Cancellation", standardDays());
        String firstPhone = "0412345678";
        String secondPhone = "0423456789";
        checkSuccess(manager.addAppointment("First Patient", firstPhone, "08:00", first), "Prepare cancellation booking 1");
        checkSuccess(manager.addAppointment("First Patient", firstPhone, "08:30", second), "Prepare cancellation booking 2");
        checkSuccess(manager.addAppointment("Second Patient", secondPhone, "09:00", first), "Prepare cancellation booking 3");
        checkSuccess(manager.addAppointment("First Patient", firstPhone, "09:30", second), "Prepare cancellation booking 4");

        checkWarning(manager.cancelAppointment(501, 510), "A cancellation must match professional and time together");
        check(manager.getAppointmentCount() == 4, "An unmatched professional/time pair leaves all bookings intact");
        checkSuccess(manager.cancelAppointment(501, 480), "Cancel by professional ID and appointment time");
        check(manager.getAppointmentCount() == 3, "A professional/time cancellation removes exactly one booking");
        check(manager.getAppointments(501).size() == 1, "Other bookings for that professional remain");
        check(manager.getAppointments(firstPhone).size() == 2, "Other bookings for that patient remain");
        checkWarning(manager.cancelAppointment(501, 480), "Report cancellation of an already-cancelled booking");
        check(manager.getAppointmentCount() == 3, "Repeating a cancellation preserves the collection");
        checkWarning(manager.cancelAppointment(9999, 480), "Report cancellation for an unknown professional");
        check(manager.getAppointmentCount() == 3, "An unknown professional cancellation preserves the collection");

        checkSuccess(manager.cancelAppointment(firstPhone), "Cancel all appointments for a patient phone");
        check(manager.getAppointmentCount() == 1, "A phone cancellation removes every matching booking");
        check(manager.getAppointments(firstPhone).isEmpty(), "No appointments remain for the cancelled phone");
        check(manager.getAppointments(secondPhone).size() == 1, "A different patient's booking is preserved");
        checkWarning(manager.cancelAppointment(firstPhone), "Report cancellation for a phone with no remaining appointments");
        check(manager.getAppointmentCount() == 1, "An absent phone cancellation preserves the collection");
        checkWarning(manager.cancelAppointment("0499999999"), "Report cancellation for an unknown phone");
        check(manager.getAppointmentCount() == 1, "An unknown phone cancellation preserves the collection");

        checkSuccess(manager.addAppointment("Second Patient", secondPhone, "08:00", first), "A cancelled slot can be booked again");
        check(manager.getAppointmentCount() == 2, "Rebooking a cancelled slot adds one appointment");
        checkSuccess(manager.cancelAppointment(secondPhone), "Cancel the final patient's remaining appointments");
        check(manager.getAppointmentCount() == 0, "Cancelling the final patient leaves the manager empty");
        check(manager.getAppointments().isEmpty(), "The complete list is empty after all cancellations");
        check(manager.getSortedAppointments().isEmpty(), "An empty complete list can be sorted");
        check(manager.getSortedAppointments(501).isEmpty(), "An empty professional-filtered list can be sorted");
        check(manager.getSortedAppointments(secondPhone).isEmpty(), "An empty patient-filtered list can be sorted");
        checkWarning(manager.cancelAppointment(501, 480), "Cancelling by professional on an empty manager reports a warning");
        checkWarning(manager.cancelAppointment(secondPhone), "Cancelling by phone on an empty manager reports a warning");
        check(manager.getAppointmentCount() == 0, "Failed cancellations cannot change an empty manager");
    }

    private static void checkCustomSchedules() {
        int[] suppliedSlots = {525, 555, 585};
        AppointmentManager manager = new AppointmentManager(suppliedSlots);
        HealthProfessional professional = new GeneralPractitioner(601, "Dr Custom", standardDays());
        Patient patient = new Patient("Schedule Patient", "0412345678");
        check(manager.getAllowedTimeSlots().length == 3, "A custom schedule replaces the default slots");
        suppliedSlots[0] = 0;
        check(manager.getAllowedTimeSlots()[0] == 525, "Changing the supplied schedule cannot change the manager");
        int[] returnedSlots = manager.getAllowedTimeSlots();
        returnedSlots[1] = 0;
        check(manager.getAllowedTimeSlots()[1] == 555, "Changing a returned schedule cannot change the manager");
        checkSuccess(manager.addAppointment(new Appointment(patient, 525, professional)), "Book the first custom slot");
        checkSuccess(manager.addAppointment(new Appointment(patient, 555, professional)), "Book a custom slot with a shifted interval");
        checkWarning(manager.addAppointment(new Appointment(patient, 480, professional)), "Reject a default slot absent from the custom schedule");
        check(manager.getAppointmentCount() == 2, "A rejected default slot preserves the custom bookings");
        checkWarning(manager.addAppointment(new Appointment(patient, 540, professional)), "Reject a time between configured custom slots");
        check(manager.getAppointmentCount() == 2, "A rejected intermediate time preserves the custom bookings");
        checkSuccess(manager.addAppointment(new Appointment(patient, 585, professional)), "Book the last custom slot");
        checkTimes(manager.getSortedAppointments(), new int[] {525, 555, 585}, "Custom bookings sort using actual appointment times");

        AppointmentManager wholeDay = new AppointmentManager(new int[] {0, 1439});
        checkSuccess(wholeDay.addAppointment(new Appointment(patient, 0, professional)), "A custom schedule may start at 00:00");
        checkSuccess(wholeDay.addAppointment(new Appointment(patient, 1439, professional)), "A custom schedule may include 23:59");
        check(wholeDay.getAppointmentCount() == 2, "Valid within-day custom boundaries are retained");

        expectInvalidSchedule(null, "Null custom schedule");
        expectInvalidSchedule(new int[0], "Empty custom schedule");
        expectInvalidSchedule(new int[] {-1}, "Negative custom slot");
        expectInvalidSchedule(new int[] {1440}, "Custom slot beyond the current day");
        expectInvalidSchedule(new int[] {480, 480}, "Duplicate custom slots");
        expectInvalidSchedule(new int[] {510, 480}, "Unsorted custom slots");
    }

    private static String[] standardDays() {
        return new String[] {"Monday", "Wednesday", "Friday"};
    }

    private static HealthProfessional[] sixProfessionals() {
        return new HealthProfessional[] {
            new GeneralPractitioner(701, "Dr General", standardDays()),
            new GeneralSurgeon(702, "Dr Surgery", standardDays()),
            new OrthopaedicDoctor(703, "Dr Bones", standardDays()),
            new Neurologist(704, "Dr Neurology", standardDays()),
            new Paediatrician(705, "Dr Children", standardDays()),
            new Nurse(706, "Nurse Care", standardDays())
        };
    }

    private static void checkSuccess(String result, String description) {
        check(result != null && result.startsWith("SUCCESS:"), description + " | Actual response: " + result);
    }

    private static void checkWarning(String result, String description) {
        check(result != null && result.startsWith("WARNING:"), description + " | Actual response: " + result);
    }

    private static void checkTimes(ArrayList<Appointment> appointments, int[] expectedTimes, String description) {
        boolean matches = appointments.size() == expectedTimes.length;
        if (matches) {
            for (int index = 0; index < expectedTimes.length; index++) {
                if (appointments.get(index).getTimeInMinutes() != expectedTimes[index]) {
                    matches = false;
                    break;
                }
            }
        }
        check(matches, description);
    }

    private static void expectInvalidProfessional(int id, String name, String[] workingDays, String description) {
        try {
            new GeneralPractitioner(id, name, workingDays);
        } catch (IllegalArgumentException expected) {
            check(true, description);
            return;
        }
        throw new AssertionError(description + " should throw IllegalArgumentException");
    }

    private static void expectInvalidPatient(String name, String phone, String description) {
        try {
            new Patient(name, phone);
        } catch (IllegalArgumentException expected) {
            check(true, description);
            return;
        }
        throw new AssertionError(description + " should throw IllegalArgumentException");
    }

    private static void expectInvalidAppointment(Patient patient, int time, HealthProfessional professional, String description) {
        try {
            new Appointment(patient, time, professional);
        } catch (IllegalArgumentException expected) {
            check(true, description);
            return;
        }
        throw new AssertionError(description + " should throw IllegalArgumentException");
    }

    private static void expectInvalidTime(String time) {
        try {
            Appointment.parseTime(time);
        } catch (IllegalArgumentException expected) {
            check(true, "Reject malformed time: " + time);
            return;
        }
        throw new AssertionError("Malformed time should throw IllegalArgumentException: " + time);
    }

    private static void expectInvalidFormattedTime(int time) {
        try {
            Appointment.formatTime(time);
        } catch (IllegalArgumentException expected) {
            check(true, "Reject an out-of-day formatted time: " + time);
            return;
        }
        throw new AssertionError("Out-of-day time should throw IllegalArgumentException: " + time);
    }

    private static void expectInvalidSchedule(int[] slots, String description) {
        try {
            new AppointmentManager(slots);
        } catch (IllegalArgumentException expected) {
            check(true, description);
            return;
        }
        throw new AssertionError(description + " should throw IllegalArgumentException");
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError(description);
        }
        passedChecks++;
    }
}
