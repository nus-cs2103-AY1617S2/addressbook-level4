//@@author A0139221N
package seedu.tasklist.commons.util;

/**
 * Helper functions for handling integers.
 */
public class IntegerUtil {
    /**
     * @param integer to test
     * @returns if the integer is a single digit
     */
    public static boolean isSingleDigit(int i) {
        return i > -10 && i < 10;
    }
}
