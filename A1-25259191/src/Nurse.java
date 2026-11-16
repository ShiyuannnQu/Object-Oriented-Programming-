
/**
 * Represents a nurse with a stated registration level.
 *
 */
public class Nurse extends HealthProfessional {
    private final String registrationLevel;

    /**
     * Creates a nurse with the default registration level Registered nurse.
     *
     * @param id the positive professional ID
     * @param name the professional's name
     * @param workingDays the professional's weekly working days
     */
    public Nurse(int id, String name, String[] workingDays) {
        this(id, name, workingDays, "Registered nurse");
    }

    /**
     * Creates a nurse with an explicit registration level.
     *
     * @param id the positive professional ID
     * @param name the professional's name
     * @param workingDays the professional's weekly working days
     * @param registrationLevel the nurse's registration level
     * @throws IllegalArgumentException if any required information is invalid
     */
    public Nurse(int id, String name, String[] workingDays, String registrationLevel) {
        super(id, name, workingDays);
        this.registrationLevel = requireText(registrationLevel, "Nurse registration level");
    }

    /**
     * Returns the nurse's registration level.
     *
     * @return the registration level
     */
    public String getRegistrationLevel() {
        return registrationLevel;
    }

    /** {@inheritDoc} */
    @Override
    public String getProfessionalType() {
        return "Nurse";
    }

    /** {@inheritDoc} */
    @Override
    public String describeService() {
        return "Provides nursing assessments and patient care as a "
                + registrationLevel + ".";
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getProfessionalType() + " {" + super.toString()
                + ", Registration level=" + registrationLevel + "}";
    }
}
