package seedu.task.commons.util;

import java.util.logging.Logger;

import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Arrays;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.Date;

//@@author A0142487Y
public class DateUtil {

    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);

    /**
     * Extracts valid information by parsing a{@code sentence} to formulate a Date object. If no valid date can be
     * inferred, an empty Date object will be returned.
     *
     * @param sentence
     * @return
     */
    public static Date extractValidDate(String sentence) {
        assert sentence != null;
        return extractValidDate(sentence.split(" "));
    }

    /**
     * Extracts valid information by parsing a {@code sentence} to formulate a Date object. If no valid date can be
     * inferred, an empty Date object will be returned.
     *
     * If a valid date is succefully extracted, the given String[] will be modified E.g. sentence is : Do work Thursday
     * -> Do work after the extraction of Thursday.
     *
     * @param sentence
     * @return
     */
    public static Date extractValidDate(String[] sentence) {

        assert sentence != null;
        Date date = null, tempDate;
        String[] words = sentence;
        String[] currentDateWords = {}; // the words that form the currently accepted date
        for (int length = words.length; length > 0; length--) {
            for (int start = 0; start + length <= words.length; start++) {
                String[] tempSentence = (String[]) Arrays.copyOfRange(words, start, start + length);
                tempDate = extractValidDate(tempSentence, length);
                if (tempDate != null) {
                    if (date == null) {
                        date = tempDate;
                        currentDateWords = tempSentence;
                    } else if (date.equalsIgnoreMinutes(tempDate)) {
                        currentDateWords = tempSentence;
                    }
                }
            }
        }
        removeUsedWordsFromSentence(sentence, currentDateWords);
        return date == null ? new Date() : date;

    }

    /**
     * Extracts valid information by parsing a given {@code number} of {@code words} to formulate a Date object. If no
     * valid date can be inferred, null will be returned.
     *
     * @param words
     * @param number
     * @return
     */
    public static Date extractValidDate(String[] words, int number) {

        assert words != null;
        assert number != 0;
        assert words.length == number;

        String testDate = StringUtil.arrayToString(words);
        try {
            return new Date(testDate);
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            logger.info(e.getMessage());
            return null;
        }
    }

    /**
     * A helper function that removes existing {@code words} from the {@code sentence}
     *
     * @param sentence
     * @param words
     */
    public static void removeUsedWordsFromSentence(String[] sentence, String[] words) {
        for (int i = 0; i < sentence.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (sentence[i].equalsIgnoreCase(words[j])) {
                    sentence[i] = "";
                    words[j] = "";
                }
            }
        }
    }

}
