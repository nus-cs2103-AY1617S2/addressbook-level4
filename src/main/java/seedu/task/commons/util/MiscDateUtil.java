package seedu.task.commons.util;

import java.util.Date;

/*
 * This class provides miscellaneous date utilities.
 */
public class MiscDateUtil {

    /*
     * Compare two dates with an allowance.
     */
    public static boolean compareDatesFuzzy(Date first, Date second, long allowance) {
        if (first == null || second == null) {
            return false;
        }

        if (Math.abs(first.getTime() - second.getTime()) < allowance) {
           return true;
        }

        return false;
    }

}