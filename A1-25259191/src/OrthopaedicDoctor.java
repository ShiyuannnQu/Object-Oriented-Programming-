
/**
 * Represents an orthopaedic doctor and an area of musculoskeletal focus.
 *
 */
public class OrthopaedicDoctor extends HealthProfessional {
    private final String orthopaedicFocus;

    /**
     * Creates a doctor whose default focus is musculoskeletal care.
     *
     * @param id the positive professional ID
     * @param name the professional's name
     * @param workingDays the professional's weekly working days
     */
    public OrthopaedicDoctor(int id, String name, String[] workingDays) {
        this(id, name, workingDays, "Musculoskeletal care");
    }

    /**
     * Creates a doctor with an explicit orthopaedic focus.
     *
     * @param id the positive professional ID
     * @param name the professional's name
     * @param workingDays the professional's weekly working days
     * @param orthopaedicFocus the doctor's area of focus
     * @throws IllegalArgumentException if any required information is invalid
     */
    public OrthopaedicDoctor(int id, String name, String[] workingDays,
            String orthopaedicFocus) {
        super(id, name, workingDays);
        this.orthopaedicFocus = requireText(orthopaedicFocus, "Orthopaedic focus");
    }

    /**
     * Returns the doctor's area of orthopaedic focus.
     *
     * @return the orthopaedic focus
     */
    public String getOrthopaedicFocus() {
        return orthopaedicFocus;
    }

    /** {@inheritDoc} */
    @Override
    public String getProfessionalType() {
        return "Orthopaedic Doctor";
    }

    /** {@inheritDoc} */
    @Override
    public String describeService() {
        return "Provides bone and joint consultations, focusing on "
                + orthopaedicFocus + ".";
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getProfessionalType() + " {" + super.toString()
                + ", Orthopaedic focus=" + orthopaedicFocus + "}";
    }
}
