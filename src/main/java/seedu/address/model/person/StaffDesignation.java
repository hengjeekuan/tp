package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the role of a Person, in ResiConnect, in their NUS residence
 */
public class StaffDesignation {

    enum Role {
        SUPPORT_STAFF, BLOCK_IC, RESIDENCE_MASTER;
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Designation should only be an integer from 0 to 2, and it should not be blank. "
            + "0 to 2 represents Support Staff, Block IC and Residence Master respectively";

    public static final String VALIDATION_REGEX = "[0-2]";

    public final Role value;

    /**
     * Constructs a {@code Designation}.
     *
     * @param designation A valid designation.
     */
    public StaffDesignation(String designation) {
        requireNonNull(designation);
        checkArgument(isValidStaffDesignation(designation), MESSAGE_CONSTRAINTS);
        value = Role.values()[Integer.parseInt(designation)];
    }

    /**
     * Returns true if a given string is a valid designation.
     */
    public static boolean isValidStaffDesignation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public int getOrdinalDesignation() {
        return this.value.ordinal();
    }

    @Override
    public String toString() {
        return value.name();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StaffDesignation)) {
            return false;
        }

        StaffDesignation otherDesignation = (StaffDesignation) other;
        return value.equals(otherDesignation.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
