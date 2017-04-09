package seedu.doist.model.util;

import org.apache.commons.lang.StringUtils;

//@@author A0147620L
public class StringMatchUtil {

    /**
     * Method that uses third party library to compute
     * how close the 'target' string is to the 'query' string, alpha-numerically
     * @param target
     * @param query
     * @return integer between 0 to 1 representing the closeness, 1 being a full match
     */
    private static double nearMatch(String target, String query) {
        return org.apache.commons.lang3.StringUtils.getJaroWinklerDistance(target, query);
    }

    /**
     * Method that returns 1 if 'query' contains 'target', 0 otherwise
     * @param target
     * @param query
     * @return an integer
     */
    private static double stringContain(String target, String query) {
        assert query != null;
        String[] words = query.split(" ");
        double value = 0.0;
        for (String word: words) {
            value += StringUtils.containsIgnoreCase(target, word) ? 1.0 : 0.0;
        }
        return value;
    }

    public static double matchValue(String target, String query) {
        return nearMatch(target, query) + stringContain(target, query);
    }

    /**
     * Method that returns a boolean if 'target' and 'query' are close enough
     * alpha-numerically, using the threshold
     * @param target
     * @param query
     * @param threshold
     * @return true if they are close enough, else false
     */
    public static boolean isNearMatch(String target, String query, double threshold) {
        return threshold < matchValue(target, query);
    }
    //@@author
}
