
/**
 * Represents the information shared by all health professionals.
 *
 */
public abstract class HealthProfessional {
    private static final String[] VALID_WORKING_DAYS = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    private final int id;
    private final String name;
    private final String[] workingDays;

    /**
     * Creates a professional with a stable identity and fixed weekly working days.
     *
     * @param id the positive numeric identifier
     * @param name the professional's name
     * @param workingDays at least one distinct day, written Monday through Sunday
     * @throws IllegalArgumentException if any supplied information is invalid
     */
    protected HealthProfessional(int id, String name, String[] workingDays) {
        if (id <= 0) {
            throw new IllegalArgumentException("Health professional ID must be positive.");
        }
        this.id = id;
        this.name = requireText(name, "Health professional name");
        this.workingDays = validateAndCopyWorkingDays(workingDays);
    }

    /**
     * Returns the identifier used to recognise this professional.
     *
     * @return the positive identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the professional's name.
     *
     * @return the name without surrounding whitespace
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a copy so callers cannot change the professional's working days.
     *
     * @return an independent array of working days
     */
    public String[] getWorkingDays() {
        String[] copy = new String[workingDays.length];
        for (int i = 0; i < workingDays.length; i++) {
            copy[i] = workingDays[i];
        }
        return copy;
    }

    /**
     * Checks whether the professional works on a named day.
     *
     * @param day the day to check, ignoring case and surrounding whitespace
     * @return true if that day is in the weekly schedule; false for null or no match
     */
    public boolean worksOn(String day) {
        if (day == null) {
            return false;
        }
        String requestedDay = day.trim();
        for (int i = 0; i < workingDays.length; i++) {
            if (workingDays[i].equalsIgnoreCase(requestedDay)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the specific kind of health professional.
     *
     * @return a readable professional type
     */
    public abstract String getProfessionalType();

    /**
     * Describes the service offered by this particular professional type.
     *
     * @return a short service description for the scripted demonstration
     */
    public abstract String describeService();

    /**
     * Checks text and removes surrounding whitespace.
     *
     * @param value the supplied text
     * @param fieldName the field name to include in validation feedback
     * @return the trimmed, nonempty text
     * @throws IllegalArgumentException if the text is null or blank
     */
    protected static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank.");
        }
        return value.trim();
    }

    private static String[] validateAndCopyWorkingDays(String[] suppliedDays) {
        if (suppliedDays == null || suppliedDays.length == 0) {
            throw new IllegalArgumentException("At least one working day is required.");
        }

        String[] copy = new String[suppliedDays.length];
        for (int i = 0; i < suppliedDays.length; i++) {
            String day = requireText(suppliedDays[i], "Working day");
            if (!isValidWorkingDay(day)) {
                throw new IllegalArgumentException(
                        "Working day must be written Monday through Sunday: " + day);
            }
            for (int j = 0; j < i; j++) {
                if (copy[j].equals(day)) {
                    throw new IllegalArgumentException("Duplicate working day: " + day);
                }
            }
            copy[i] = day;
        }
        return copy;
    }

    private static boolean isValidWorkingDay(String day) {
        for (int i = 0; i < VALID_WORKING_DAYS.length; i++) {
            if (VALID_WORKING_DAYS[i].equals(day)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compares professional identities, including across different subclasses.
     *
     * @param other the object to compare
     * @return true if both objects represent the same professional ID
     */
    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HealthProfessional)) {
            return false;
        }
        HealthProfessional otherProfessional = (HealthProfessional) other;
        return id == otherProfessional.id;
    }

    /**
     * Returns a hash value consistent with ID-based equality.
     *
     * @return the stable professional ID
     */
    @Override
    public final int hashCode() {
        return id;
    }

    /**
     * Presents the details common to every professional.
     *
     * @return a readable description of the identifier, name and working days
     */
    @Override
    public String toString() {
        String days = "";
        for (int i = 0; i < workingDays.length; i++) {
            if (i > 0) {
                days += ", ";
            }
            days += workingDays[i];
        }
        return "ID=" + id + ", Name=" + name + ", Working days=[" + days + "]";
    }
}
