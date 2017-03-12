package seedu.toluist.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    private static final String HYPHEN = "-";

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        assert word != null : "Word parameter cannot be null";
        assert sentence != null : "Sentence parameter cannot be null";

        String preppedWord = word.trim();
        assert !preppedWord.isEmpty() : "Word parameter cannot be empty";
        assert preppedWord.split("\\s+").length == 1 : "Word parameter should be a single word";

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            if (wordInSentence.equalsIgnoreCase(preppedWord)) return true;
        }
        return false;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        assert t != null;
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     * Will return false if the string is:
     * null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s) {
        return s != null && s.matches("^0*[1-9]\\d*$");
    }

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
        String[] splittedStringIndexes = processedStringIndexes.split(" |\\,");
        splittedStringIndexes = Arrays.
                stream(splittedStringIndexes).
                filter(s -> !s.isEmpty()).
                toArray(String[]::new);

        // Process formatted stringIndexes
        List<Integer> indexes = new ArrayList<Integer>();
        int i = 0;
        while (i < splittedStringIndexes.length) {
            String splittedStringIndex = splittedStringIndexes[i];
            if (StringUtil.isUnsignedInteger(splittedStringIndex)) {
                int index = Integer.valueOf(splittedStringIndex);
                if (index <= maxIndex) {
                    indexes.add(Integer.valueOf(splittedStringIndex));
                    i += 1;
                } else {
                    // Invalid state, early termination
                    return indexes;
                }
            } else if (splittedStringIndex.equals(HYPHEN)) {
                // If stringIndexes starts with "-", the startIndex will be 0;
                int startIndex = (indexes.isEmpty()) ? 0 : indexes.get(indexes.size() - 1);
                // If stringIndexes ends with "-", the endIndex will be todoListSize
                int endIndex = maxIndex;
                if (i + 1 < splittedStringIndexes.length
                        && StringUtil.isUnsignedInteger(splittedStringIndexes[i + 1])) {
                    endIndex = Integer.valueOf(splittedStringIndexes[i + 1]);
                } else if (i + 1 > splittedStringIndexes.length) {
                    // Invalid state, early termination
                    return indexes;
                }
                for (int value = startIndex + 1; value <= Integer.min(endIndex, maxIndex); value++) {
                    indexes.add(value);
                }
                i += 2;
            } else {
                // Invalid state, early termination
                return indexes;
            }
        }
        return indexes;
    }
}
