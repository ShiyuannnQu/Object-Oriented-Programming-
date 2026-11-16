
/**
 * Stores the immutable contact details of a patient.
 *
 * <p>The phone rule of 7 to 15 digits is a simplified teaching assumption.
 * Keeping the phone as text preserves any leading zero.</p>
 */
public final class Patient {
    private final String name;
    private final String mobilePhone;

    /**
     * Creates a patient after validating the required contact details.
     *
     * @param name the patient's nonblank name
     * @param mobilePhone the contact number, containing 7 to 15 digits
     * @throws IllegalArgumentException if either value is missing or invalid
     */
    public Patient(String name, String mobilePhone) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name must not be blank.");
        }
        if (mobilePhone == null || mobilePhone.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient mobile phone must not be blank.");
        }

        String checkedPhone = mobilePhone.trim();
        if (checkedPhone.length() < 7 || checkedPhone.length() > 15) {
            throw new IllegalArgumentException("Patient mobile phone must contain 7 to 15 digits.");
        }
        for (int i = 0; i < checkedPhone.length(); i++) {
            char character = checkedPhone.charAt(i);
            if (character < '0' || character > '9') {
                throw new IllegalArgumentException("Patient mobile phone must contain digits only.");
            }
        }

        this.name = name.trim();
        this.mobilePhone = checkedPhone;
    }

    /**
     * Returns the patient's name.
     *
     * @return the name without surrounding whitespace
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the contact number without losing leading zeros.
     *
     * @return the mobile phone number as text
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Presents the patient details needed by an appointment.
     *
     * @return a readable name and contact number
     */
    @Override
    public String toString() {
        return "Patient {Name=" + name + ", Mobile phone=" + mobilePhone + "}";
    }
}
