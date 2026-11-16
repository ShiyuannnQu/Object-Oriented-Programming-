
/**
 * Represents a paediatrician with a maximum patient age for this simulation.
 *
 * <p>The permitted range of 1 to 18 is a simplified teaching assumption,
 * rather than a statement of clinical or legal practice.</p>
 */
public class Paediatrician extends HealthProfessional {
    private final int maximumPatientAge;

    /**
     * Creates a paediatrician with a default maximum patient age of 18.
     *
     * @param id the positive professional ID
     * @param name the professional's name
     * @param workingDays the professional's weekly working days
     */
    public Paediatrician(int id, String name, String[] workingDays) {
        this(id, name, workingDays, 18);
    }

    /**
     * Creates a paediatrician with an explicit maximum patient age.
     *
     * @param id the positive professional ID
     * @param name the professional's name
     * @param workingDays the professional's weekly working days
     * @param maximumPatientAge the maximum age, from 1 to 18 inclusive
     * @throws IllegalArgumentException if required details or the age are invalid
     */
    public Paediatrician(int id, String name, String[] workingDays,
            int maximumPatientAge) {
        super(id, name, workingDays);
        if (maximumPatientAge < 1 || maximumPatientAge > 18) {
            throw new IllegalArgumentException(
                    "Maximum patient age must be from 1 to 18 in this simulation.");
        }
        this.maximumPatientAge = maximumPatientAge;
    }

    /**
     * Returns the maximum patient age used in this simulation.
     *
     * @return the maximum age, from 1 to 18 inclusive
     */
    public int getMaximumPatientAge() {
        return maximumPatientAge;
    }

    /** {@inheritDoc} */
    @Override
    public String getProfessionalType() {
        return "Paediatrician";
    }

    /** {@inheritDoc} */
    @Override
    public String describeService() {
        return "Provides children's health consultations up to age "
                + maximumPatientAge + " in this simulation.";
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getProfessionalType() + " {" + super.toString()
                + ", Maximum patient age=" + maximumPatientAge + "}";
    }
}
