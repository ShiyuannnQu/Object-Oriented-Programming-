import java.util.ArrayList;

/** A scripted, fictional demonstration of the current-day appointment model. */
public class AssignmentOne {
    private AssignmentOne() {
    }

    /**
     * Exercises professional polymorphism and every appointment operation.
     * @param args unused: the assessment uses a scripted console demonstration
     */
    public static void main(String[] args) {
        System.out.println("A1 APPOINTMENT SYSTEM");
        System.out.println("All patient data is fictional. Bookings represent the current day only.");
        System.out.println("Assumptions: 16:00 is an allowed start; phone cancellation removes all matches.");
        System.out.println("Working days are stored as staff details; no extra day-of-week booking rule is added.");

        String[] clinicDays = {"Monday", "Wednesday", "Friday"};
        HealthProfessional[] professionals = {
            new GeneralPractitioner(101, "Dr Alex Chen", clinicDays, true),
            new GeneralPractitioner(102, "Dr Sam Patel", clinicDays, false),
            new GeneralPractitioner(103, "Dr Jamie Lee", clinicDays),
            new GeneralSurgeon(201, "Dr Taylor Morgan", clinicDays, "Abdominal surgery"),
            new OrthopaedicDoctor(301, "Dr Jordan Kim", clinicDays, "Joint care"),
            new Neurologist(401, "Dr Casey Brown", clinicDays, "Epilepsy"),
            new Paediatrician(501, "Dr Riley Wilson", clinicDays, 18),
            new Nurse(601, "Robin Davis", clinicDays, "Registered nurse")
        };

        heading("PARTS 1-3: eight professionals, six concrete types");
        for (HealthProfessional professional : professionals) {
            System.out.println(professional);
            System.out.println("  Service: " + professional.describeService());
        }
        System.out.println("ID 101 works on Monday: " + professionals[0].worksOn("Monday"));

        AppointmentManager manager = new AppointmentManager();
        heading("PARTS 4-6: configured slots and initially empty collection");
        printSlots(manager);
        show(manager, "Initial appointments", manager.getAppointments());

        heading("ADD: deliberately unsorted bookings across every professional type");
        Patient ava = new Patient("Ava Smith", "0412345678");
        Appointment first = new Appointment(ava, Appointment.parseTime("11:00"), professionals[0]);
        System.out.println(manager.addAppointment(first));
        System.out.println(manager.addAppointment("Noah Jones", "0412345679", "09:00", professionals[3]));
        System.out.println(manager.addAppointment("Ava Smith", "0412345678", "08:30", professionals[5]));
        System.out.println(manager.addAppointment("Mia Williams", "0412345680", "09:00", professionals[4]));
        System.out.println(manager.addAppointment("Leo Evans", "0412345681", "13:30", professionals[6]));
        System.out.println(manager.addAppointment("Isla Taylor", "0412345682", "10:00", professionals[7]));
        System.out.println(manager.addAppointment("Ethan Clark", "0412345683", "16:00", professionals[1]));
        System.out.println(manager.addAppointment("Sofia Hall", "0412345684", "08:00", professionals[2]));
        System.out.println(manager.addAppointment("Ava Smith", "0412345678", "09:30", professionals[0]));
        System.out.println("Current appointment count: " + manager.getAppointmentCount());

        show(manager, "VIEW ALL: insertion order", manager.getAppointments());
        show(manager, "SORT ALL: earliest to latest", manager.getSortedAppointments());
        show(manager, "FILTER: professional ID 101", manager.getAppointments(101));
        show(manager, "FILTER AND SORT: professional ID 101", manager.getSortedAppointments(101));
        show(manager, "FILTER: patient phone 0412345678", manager.getAppointments("0412345678"));
        show(manager, "FILTER AND SORT: patient phone 0412345678",
                manager.getSortedAppointments("0412345678"));
        show(manager, "FILTER: nonexistent professional ID", manager.getAppointments(999));
        show(manager, "FILTER: nonexistent patient phone", manager.getAppointments("0499999999"));

        heading("ERROR CASES: rejected requests leave the collection unchanged");
        int beforeRejectedRequests = manager.getAppointmentCount();
        HealthProfessional sameIdentity = new GeneralPractitioner(101, "Dr Alex Chen", clinicDays);
        System.out.println("Two distinct objects with ID 101 are equal: "
                + professionals[0].equals(sameIdentity));
        System.out.println(manager.addAppointment("Duplicate Booking", "0412345685", "11:00", sameIdentity));
        System.out.println(manager.addAppointment("Invalid Slot", "0412345685", "08:15", professionals[0]));
        System.out.println(manager.addAppointment("Too Early", "0412345685", "07:30", professionals[0]));
        System.out.println(manager.addAppointment("Too Late", "0412345685", "16:30", professionals[0]));
        System.out.println(manager.addAppointment(" ", "0412345685", "12:00", professionals[0]));
        System.out.println(manager.addAppointment("Invalid Contact", "phone", "12:00", professionals[0]));
        System.out.println(manager.addAppointment("Missing Professional", "0412345685", "12:00", null));
        System.out.println(manager.addAppointment("Invalid Time", "0412345685", "9:00", professionals[0]));
        System.out.println(manager.addAppointment((Appointment) null));
        System.out.println("Appointment count before/after rejected requests: "
                + beforeRejectedRequests + "/" + manager.getAppointmentCount());

        heading("CANCEL: professional ID plus time, then show the update");
        System.out.println(manager.cancelAppointment(101, Appointment.parseTime("09:30")));
        show(manager, "Professional ID 101 after cancellation", manager.getSortedAppointments(101));
        System.out.println(manager.cancelAppointment(101, Appointment.parseTime("09:30")));

        heading("CANCEL: all remaining appointments for one patient phone");
        System.out.println(manager.cancelAppointment("0412345678"));
        show(manager, "Patient after cancellation", manager.getAppointments("0412345678"));
        System.out.println(manager.cancelAppointment("0412345678"));
        show(manager, "Updated full appointment collection", manager.getSortedAppointments());
        System.out.println("Final current-day appointment count: " + manager.getAppointmentCount());

        heading("CONFIGURATION: replacement slots, including a quarter-hour start");
        int[] replacementSlots = {9 * 60, 9 * 60 + 15, 10 * 60};
        AppointmentManager alternate = new AppointmentManager(replacementSlots);
        printSlots(alternate);
        System.out.println(alternate.addAppointment("Custom Schedule", "0412345686", "09:15", professionals[3]));
        System.out.println(alternate.addAppointment("Outside Schedule", "0412345687", "08:00", professionals[3]));
        show(alternate, "Custom schedule sorted appointments", alternate.getSortedAppointments());
        System.out.println(alternate.cancelAppointment("0412345686"));
        show(alternate, "Custom schedule after cancellation", alternate.getAppointments());
    }

    private static void heading(String text) {
        System.out.println();
        System.out.println("=== " + text + " ===");
    }

    private static void show(AppointmentManager manager, String label,
            ArrayList<Appointment> appointments) {
        heading(label);
        System.out.println(manager.formatAppointments(appointments));
    }

    private static void printSlots(AppointmentManager manager) {
        System.out.print("Allowed start times: ");
        for (int slot : manager.getAllowedTimeSlots()) {
            System.out.print(Appointment.formatTime(slot) + " ");
        }
        System.out.println();
    }
}
