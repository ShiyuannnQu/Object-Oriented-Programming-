
/**
 * Represents a neurologist with a stated neurological focus.
 *
 */
public class Neurologist extends HealthProfessional {
    private final String neurologicalFocus;

    /**
     * Creates a neurologist whose default focus is general neurology.
     *
     * @param id the positive professional ID
     * @param name the professional's name
     * @param workingDays the professional's weekly working days
     */
    public Neurologist(int id, String name, String[] workingDays) {
        this(id, name, workingDays, "General neurology");
    }

    /**
     * Creates a neurologist with an explicit focus.
     *
     * @param id the positive professional ID
     * @param name the professional's name
     * @param workingDays the professional's weekly working days
     * @param neurologicalFocus the neurologist's area of focus
     * @throws IllegalArgumentException if any required information is invalid
     */
    public Neurologist(int id, String name, String[] workingDays,
            String neurologicalFocus) {
        super(id, name, workingDays);
        this.neurologicalFocus = requireText(neurologicalFocus, "Neurological focus");
    }

    /**
     * Returns the neurologist's area of focus.
     *
     * @return the neurological focus
     */
    public String getNeurologicalFocus() {
        return neurologicalFocus;
    }

    /** {@inheritDoc} */
    @Override
    public String getProfessionalType() {
        return "Neurologist";
    }

    /** {@inheritDoc} */
    @Override
    public String describeService() {
        return "Provides neurological consultations, focusing on "
                + neurologicalFocus + ".";
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getProfessionalType() + " {" + super.toString()
                + ", Neurological focus=" + neurologicalFocus + "}";
    }
}
