package seedu.toluist.controller.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.toluist.commons.util.StringUtil;

/**
 * Tokenize string of indexes into a list of integer index.
 */
public class IndexTokenizer {
    private static final String HYPHEN = "-";

    /**
     * Returns the index if indexToken holds a valid index number, else returns -1
     * @param indexToken the string holding the index number
     */
    public static int getIndex(String indexToken) {
        return indexToken == null ? -1 : Integer.parseInt(indexToken) - 1;
    }

    /**
     * Splits a string of indexes into a list.
     *   Examples:
     *   splitIndexes(" - 3",           8) -> [1, 2, 3]
     *   splitIndexes(" 3 -",           8) -> [3, 4, 5, 6, 7, 8]
     *   splitIndexes("3 4,  5",        8) -> [3, 4, 5]
     *   splitIndexes("3 - 5",          8) -> [3, 4, 5]
     *   splitIndexes("- 3, 5, 7 - 12", 8) -> [1, 2, 3, 5, 7, 8]
     * This function is robust enough to handle excessive white spaces (" ") and commas (",").
     * @param stringIndexes, the unprocessed string of indexes from user's input
     * @param maxIndex, the maximum possible index number
     * @return a list of integer containing all valid indexes
     */
    public static List<Integer> splitIndexes(String stringIndexes, int maxIndex) {
        // Prepare stringIndexes in the correct format to be processed
        // Correct format example: ["2", "-", "5", "7", "11", "-", "13", "15"]
        String processedStringIndexes = stringIndexes.replaceAll(HYPHEN, " - ");
        String[] splittedStringIndexes = processedStringIndexes.split(" |,");
        splittedStringIndexes = Arrays.
                stream(splittedStringIndexes).
                filter(s -> !s.isEmpty()).
                toArray(String[]::new);

        // Process formatted stringIndexes
        List<Integer> indexes = new ArrayList<Integer>();
        int i = 0;
        while (i < splittedStringIndexes.length) {
            String splittedStringIndex = splittedStringIndexes[i];
            if (!StringUtil.isPositiveInteger(splittedStringIndex) && !splittedStringIndex.equals(HYPHEN)) {
                // Invalid state, early termination
                return indexes;
            }
            if (StringUtil.isPositiveInteger(splittedStringIndex)) {
                int index = Integer.valueOf(splittedStringIndex);
                if (index > maxIndex) {
                    // Invalid state, early termination
                    return indexes;
                }
                indexes.add(Integer.valueOf(splittedStringIndex));
                i++;
            } else if (splittedStringIndex.equals(HYPHEN)) {
                // If stringIndexes starts with "-", the startIndex will be 0
                int startIndex = (indexes.isEmpty()) ? 0 : indexes.get(indexes.size() - 1);
                // If stringIndexes ends with "-", the endIndex will be maxIndex
                int endIndex = maxIndex;
                if (i + 1 > splittedStringIndexes.length
                        || (i + 1 < splittedStringIndexes.length
                                && !StringUtil.isPositiveInteger(splittedStringIndexes[i + 1]))) {
                    // Invalid state, early termination
                    return indexes;
                }
                // Valid states: Negation of the above if-statement, which leads to the following 2 cases.
                // If (i + 1 == splittedStringIndexes.length) is true, let endIndex = maxIndex
                // If (next splittedStringIndex) is a positive integer, let endIndex = next splittedStringIndex.
                if (i + 1 < splittedStringIndexes.length) {
                    endIndex = Integer.valueOf(splittedStringIndexes[i + 1]);
                }
                for (int value = startIndex + 1; value <= Integer.min(endIndex, maxIndex); value++) {
                    indexes.add(value);
                }
                i += 2;
            }
        }
        return indexes;
    }

}
