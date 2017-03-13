package seedu.task.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;

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
     * Returns true if the {@code sentence} contains the {@code keywords}.
     *   Ignores case, but a full word match is required for each keyword.
     * @param sentence cannot be null
     * @param keywords cannot be null, cannot be empty, must a set of words
     */
    public static boolean containsExactWordsIgnoreCase(String sentence, Set<String> keywords) {

        String lowerCaseSentence = sentence.toLowerCase();
        Set<String> lowerCaseKeywords = new HashSet<>(keywords.size());
        for (String s : keywords) {
            String a = s.toLowerCase();
            keywords.remove(s);
            lowerCaseKeywords.add(a);
        }
        String[] tokenizedTaskName = lowerCaseSentence.split(" ");
        Set<String> tokenizedTaskNameInSet = new HashSet<>(Arrays.asList(tokenizedTaskName));
        return tokenizedTaskNameInSet.containsAll(keywords);
    }

    /**
     * Returns true if {@code tags} contains the {@code keyword}.
     * Ignores case, but a full word match is required.
     *
     * @param tags may be null
     * @param keyword cannot be null, cannot be empty, must be a single word
     * @return
     */
    public static boolean containsTagIgnoreCase(UniqueTagList tags, String keyword) {
        for (Tag t : tags) {
            if (containsWordIgnoreCase((t.tagName), keyword)) {
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
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     * Will return false if the string is:
     * null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s) {
        return s != null && s.matches("^0*[1-9]\\d*$");
    }

}
