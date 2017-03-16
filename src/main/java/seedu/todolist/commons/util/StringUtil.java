package seedu.todolist.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

import seedu.todolist.model.tag.Tag;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, a full word match is not required.
     *   <br>examples:<pre>
     *       containsIgnoreCase("ABc def", "abc") == true
     *       containsIgnoreCase("ABc def", "DEF") == true
     *       containsIgnoreCase("ABc def", "AB") == true
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsIgnoreCase(String name, Set<Tag> tags, String word) {
        assert word != null : "Word parameter cannot be null";
        assert name != null : "Sentence parameter cannot be null";


        String preppedWord = word.trim();
        assert !preppedWord.isEmpty() : "Word parameter cannot be empty";

        //if word passed in is a search for a tag t/
        if (preppedWord.startsWith("t/")) {
            String tagKeyword = preppedWord.substring(2);
            //the tag must be a full tag match
            for (Tag tag: tags) {
                if (tag.getTagName().toLowerCase().equals(tagKeyword.toLowerCase())) {
                    return true;
                }
            }
        } else {
            if (name.toLowerCase().contains(preppedWord.toLowerCase())) return true;
        }

        return false;

        //String[] wordsInPreppedSentence = preppedWord.split("\\s+");
        //assert preppedWord.split("\\s+").length == 1 : "Word parameter should be a single word";

        //String preppedSentence = sentence;
        //String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        //for (String stringQuery: wordsInPreppedSentence) {

        //}

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
