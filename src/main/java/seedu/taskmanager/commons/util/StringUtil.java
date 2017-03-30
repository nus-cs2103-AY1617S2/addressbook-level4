package seedu.taskmanager.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    // @@author A0140032E
    private static final int TOLERANCE_SIMPLE_RATIO = 75;
    private static final int TOLERANCE_PARTIAL_RATIO = 75;
    private static final int TOLERANCE_TOKEN_SORT_PARTIAL_RATIO = 75;
    private static final int TOLERANCE_TOKEN_SORT_RATIO = 75;
    // @@author
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
            if (wordInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }

        // @@author A0140032E
        int simpleRatio = FuzzySearch.ratio(preppedSentence, preppedWord);
        int partialRatio = FuzzySearch.partialRatio(preppedSentence, preppedWord);
        int tokenSortPartialRatio = FuzzySearch.tokenSortPartialRatio(preppedSentence, preppedWord);
        int tokenSortRatio = FuzzySearch.tokenSortRatio(preppedSentence, preppedWord);
        if (simpleRatio >= TOLERANCE_SIMPLE_RATIO ||
                partialRatio >= TOLERANCE_PARTIAL_RATIO ||
                tokenSortPartialRatio >= TOLERANCE_TOKEN_SORT_PARTIAL_RATIO ||
                tokenSortRatio >= TOLERANCE_TOKEN_SORT_RATIO) {
            return true;
        }
        // @@author
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
}
