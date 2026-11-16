
/**
 * Represents a general practitioner and whether bulk billing is offered.
 *
 */
public class GeneralPractitioner extends HealthProfessional {
    private final boolean bulkBilling;

    /**
     * Creates a general practitioner with bulk billing disabled by default.
     *
     * @param id the positive professional ID
     * @param name the professional's name
     * @param workingDays the professional's weekly working days
     */
    public GeneralPractitioner(int id, String name, String[] workingDays) {
        this(id, name, workingDays, false);
    }

    /**
     * Creates a general practitioner with an explicit billing setting.
     *
     * @param id the positive professional ID
     * @param name the professional's name
     * @param workingDays the professional's weekly working days
     * @param bulkBilling whether the professional offers bulk billing
     * @throws IllegalArgumentException if shared professional details are invalid
     */
    public GeneralPractitioner(int id, String name, String[] workingDays,
            boolean bulkBilling) {
        super(id, name, workingDays);
        this.bulkBilling = bulkBilling;
    }

    /**
     * Returns whether bulk billing is offered.
     *
     * @return true if bulk billing is available
     */
    public boolean isBulkBilling() {
        return bulkBilling;
    }

    /** {@inheritDoc} */
    @Override
    public String getProfessionalType() {
        return "General Practitioner";
    }

    /** {@inheritDoc} */
    @Override
    public String describeService() {
        return "Provides general consultations and preventive health care.";
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getProfessionalType() + " {" + super.toString()
                + ", Bulk billing=" + bulkBilling + "}";
    }
}
