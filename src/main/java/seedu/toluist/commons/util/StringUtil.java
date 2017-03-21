package seedu.toluist.commons.util;

import org.atteo.evo.inflector.English;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if string is not null and not empty
     * @param string
     */
    public static boolean isPresent(String string) {
        return string != null && !string.isEmpty();
    }

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
     * Returns true if s represents a positive integer e.g. 1, 2, 3, ... <br>
     * Will return false if the string is:
     * null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isPositiveInteger(String s) {
        return s != null && s.matches("^0*[1-9]\\d*$");
    }

    /**
     * Given a noun and a count, return the count followed by the noun in singular/plural form appropriately
     * E.g:
     * "word", 1 -> "1 word"
     * "cat", 2 -> "2 cats"
     * @param noun a noun
     * @param count the quantity
     * @return noun with count
     */
    public static String nounWithCount(String noun, int count) {
        return count + " " + English.plural(noun, count);
    }
}
