package seedu.address.model.person;

import java.util.Arrays;

/**
 * Represents the levels of Priority that are available
 *
 */
public enum PriorityLevel {
    high,
    medium,
    low;


    public static boolean isInEnum(String value) {
        return Arrays.stream(PriorityLevel.values()).anyMatch(e -> e.name().equals(value));
    }

}
