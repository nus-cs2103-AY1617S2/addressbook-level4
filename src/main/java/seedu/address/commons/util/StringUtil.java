package seedu.address.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

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

    //@@author A0163848R
    /**
     * Extracts space-concatenated string until end or the first of the last instances of the passed haltAt keywords
     * @param Keywords to parse
     * @param Index to start at
     * @param Keywords to stop at once the first of the set of last instances is met
     * @return Concatenated string of all keywords between start and index specified by the keywords parameter
     */
    public static String extract(List<String> keywords, int start, List<String> haltAt) {
        return StringUtil.concat(keywords.subList(
                start,
                start + CollectionUtil.firstLastIndexOfAny(keywords.subList(start, keywords.size()), haltAt)));
    }

    /**
     * Concatenates Strings with the space character
     * @param Strings to concatenate
     * @return Concatenated String
     */
    public static String concat(List<String> strs) {
        return StringUtil.concat(strs, " ");
    }

    /**
     * Concatenates Strings with the specified character
     * @param Strings to concatenate
     * @param String to repeat between strs
     * @return Concatenated String
     */
    public static String concat(List<String> strs, String between) {
        String cat = "";
        for (int i = 0; i < strs.size(); i++) {
            if (i == 0) {
                cat = strs.get(i);
                continue;
            }

            cat += between + strs.get(i);
        }
        return cat;
    }
  //@@author
}
