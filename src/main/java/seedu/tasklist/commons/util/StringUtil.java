package seedu.tasklist.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

//@@author A0139221N
    /**
     * Returns true if the {@code sentence} contains {@code wordSet}.
     *   Ignores case, but starting letters must match the wordSet.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", ["ab"]) == true
     *       containsWordIgnoreCase("ABc def", ["ABC", "DEF"]) == true
     *       containsWordIgnoreCase("ABc def", ["AB", "g"]) == false //must contain all in the set
     *       </pre>
     * @param sentence cannot be null
     * @param wordSet cannot be null, cannot be empty
     */
    public static boolean containsStartingLettersIgnoreCase(String sentence, Set<String> wordSet) {
        assert wordSet != null : "WordSet parameter cannot be null";
        assert sentence != null : "Sentence parameter cannot be null";
        assert !wordSet.isEmpty() : "WordSet parameter cannot be empty";

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        boolean isAllWordsPresent = true;
        for (String word : wordSet) {
            word = word.trim();
            assert word.split("\\s+").length == 1 : "Word in wordSet should be a single word";
            boolean isCurrentPresent = false;
            for (String wordInSentence: wordsInPreppedSentence) {
                if (wordInSentence.toLowerCase().trim().startsWith(word.toLowerCase())) {
                    isCurrentPresent = true;
                }
            }
            isAllWordsPresent = isCurrentPresent && isAllWordsPresent;
        }
        return isAllWordsPresent;
    }
//@@author

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

//@@author A0139221N
    /**
     * Returns a string with its square brackets, [ ]
     */
    public static String removeSquareBrackets(String tag) {
        if (tag.startsWith("[") && tag.endsWith("]")) {
            return tag.substring(1, tag.length() - 1);
        } else {
            return tag;
        }
    }
}
