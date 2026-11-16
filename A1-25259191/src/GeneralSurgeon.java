
/**
 * Represents a general surgeon with a stated area of surgical focus.
 *
 */
public class GeneralSurgeon extends HealthProfessional {
    private final String surgicalFocus;

    /**
     * Creates a surgeon whose default focus is general surgery.
     *
     * @param id the positive professional ID
     * @param name the professional's name
     * @param workingDays the professional's weekly working days
     */
    public GeneralSurgeon(int id, String name, String[] workingDays) {
        this(id, name, workingDays, "General surgery");
    }

    /**
     * Creates a surgeon with an explicit surgical focus.
     *
     * @param id the positive professional ID
     * @param name the professional's name
     * @param workingDays the professional's weekly working days
     * @param surgicalFocus the surgeon's area of focus
     * @throws IllegalArgumentException if any required information is invalid
     */
    public GeneralSurgeon(int id, String name, String[] workingDays,
            String surgicalFocus) {
        super(id, name, workingDays);
        this.surgicalFocus = requireText(surgicalFocus, "Surgical focus");
    }

    /**
     * Returns the surgeon's area of focus.
     *
     * @return the surgical focus
     */
    public String getSurgicalFocus() {
        return surgicalFocus;
    }

    /** {@inheritDoc} */
    @Override
    public String getProfessionalType() {
        return "General Surgeon";
    }

    /** {@inheritDoc} */
    @Override
    public String describeService() {
        return "Provides surgical consultations, focusing on " + surgicalFocus + ".";
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getProfessionalType() + " {" + super.toString()
                + ", Surgical focus=" + surgicalFocus + "}";
    }
}
