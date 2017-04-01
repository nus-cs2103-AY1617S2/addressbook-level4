package seedu.toluist.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.atteo.evo.inflector.English;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    //@@author A0127545A
    /**
     * Returns true if string is not null and not empty
     * @param string
     */
    public static boolean isPresent(String string) {
        return string != null && !string.trim().isEmpty();
    }

    //@@author
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

    //@@author A0131125Y
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

    //@@author A0162011A
    /**
     * Returns an array containing each word in the string.
     * @param string
     */
    public static String[] convertToArray(String keywords) {
        if (keywords == null || keywords.trim().isEmpty()) {
            return new String[] { "" };
        }

        String trimmedKeywords = keywords.trim();
        String[] keywordList = trimmedKeywords.split(" ");
        ArrayList<String> replacementList = new ArrayList<>();
        for (String keyword : keywordList) {
            if (!keyword.equals("")) {
                replacementList.add(keyword);
            }
        }
        return replacementList.toArray(new String[0]);
    }

    /**
     * Return a transformed string of an original string, with the last word replaced
     * @param s the original string
     * @param replacement the string to replace the first word
     * @return the transformed string
     */
    public static String replaceLastWord(String s, String replacement) {
        if (replacement == null || !StringUtil.isPresent(s)) {
            return s;
        }

        String[] words = s.split("\\s+");

        if (words.length == 0) {
            return s;
        }

        words[words.length - 1] = replacement;
        return String.join(" ", words);
    }

    /**
     * Return a transformed string of an original string, with the first word replaced
     * @param s the original string
     * @param replacement the string to replace the first word
     * @return the transformed string
     */
    public static String replaceFirstWord(String s, String replacement) {
        if (replacement == null || !StringUtil.isPresent(s)) {
            return s;
        }

        String[] words = s.split("\\s+");

        if (words.length == 0) {
            return s;
        }

        words[0] = replacement;
        return String.join(" ", words);
    }

    /**
     * Get last word of a string
     * @param s a string
     * @return last word of the string
     */
    public static String getLastWord(String s) {
        String[] words = s.trim().split("\\s+");
        return words[words.length - 1];
    }

    /**
     * Check if a string starts with another phrase, case-ignored
     * @param s a string
     * @param comparision a phrase to compare with
     * @return true / false
     */
    public static boolean startsWithIgnoreCase(String s, String comparision) {
        if (s == null || comparision == null) {
            return false;
        }
        return s.toLowerCase().startsWith(comparision.toLowerCase());
    }
}
