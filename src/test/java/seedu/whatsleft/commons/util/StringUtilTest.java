package seedu.whatsleft.commons.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class StringUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // ---------------- Tests for isUnsignedPositiveInteger --------------------------------------

    @Test
    public void isUnsignedPositiveInteger() {

        // Equivalence partition: null
        assertFalse(StringUtil.isUnsignedInteger(null));

        // EP: empty strings
        assertFalse(StringUtil.isUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isUnsignedInteger("a"));
        assertFalse(StringUtil.isUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isUnsignedInteger("0"));

        // EP: signed numbers
        assertFalse(StringUtil.isUnsignedInteger("-1"));
        assertFalse(StringUtil.isSignedInteger("+1"));
        // EP: numbers with white space
        assertFalse(StringUtil.isUnsignedInteger(" 10 ")); // Leading/trailing
                                                           // spaces
        assertFalse(StringUtil.isUnsignedInteger("1 0")); // Spaces in the
                                                          // middle

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isUnsignedInteger("10"));
    }

    //author A0121668A
    // ---------------- Tests for isSignedPositiveInteger --------------------------------------

    @Test
    public void isSignedPositiveInteger() {

        // Equivalence partition: null
        assertFalse(StringUtil.isSignedInteger(null));

        // EP: empty strings
        assertFalse(StringUtil.isSignedInteger("")); // Boundary value
        assertFalse(StringUtil.isSignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isSignedInteger("a"));
        assertFalse(StringUtil.isSignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isSignedInteger("0"));

        // EP: signed numbers
        assertTrue(StringUtil.isSignedInteger("-1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isSignedInteger(" 10 ")); // Leading/trailing
                                                           // spaces
        assertFalse(StringUtil.isSignedInteger("1 0")); // Spaces in the
                                                          // middle

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isSignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isSignedInteger("10"));
    }
    //@@author
    // ---------------- Tests for containsWordIgnoreCase
    // --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null The four test cases
     * below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCaseNullWordExceptionThrown() {
        assertExceptionThrown("typical sentence", null, "Word parameter cannot be null");
    }

    private void assertExceptionThrown(String sentence, String word, String errorMessage) {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(errorMessage);
        StringUtil.containsWordIgnoreCase(sentence, word);
    }

    @Test
    public void containsWordIgnoreCaseEmptyWordExceptionThrown() {
        assertExceptionThrown("typical sentence", "  ", "Word parameter cannot be empty");
    }

    @Test
    public void containsWordIgnoreCaseMultipleWordsExceptionThrown() {
        assertExceptionThrown("typical sentence", "aaa BBB", "Word parameter should be a single word");
    }

    @Test
    public void containsWordIgnoreCaseNullSentenceExceptionThrown() {
        assertExceptionThrown(null, "abc", "Sentence parameter cannot be null");
    }

    /*
     * Valid equivalence partitions for word: - any word - word containing
     * symbols/numbers - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence: - empty string - one word -
     * multiple words - sentence with extra spaces
     *
     * Possible scenarios returning true: - matches first word in sentence -
     * last word in sentence - middle word in sentence - matches multiple words
     *
     * Possible scenarios returning false: - query word matches part of a
     * sentence word - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low
     * number of test cases.
     */

    @Test
    public void containsWordIgnoreCaseValidInputsCorrectResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCase("", "abc")); // Boundary
                                                                   // case
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb")); // Sentence
                                                                             // word
                                                                             // bigger
                                                                             // than
                                                                             // query
                                                                             // word
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb")); // Query
                                                                               // word
                                                                               // bigger
                                                                               // than
                                                                               // sentence
                                                                               // word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb")); // First
                                                                             // word
                                                                             // (boundary
                                                                             // case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last
                                                                                 // word
                                                                                 // (boundary
                                                                                 // case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence
                                                                                     // has
                                                                                     // extra
                                                                                     // spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa")); // Only one
                                                                     // word in
                                                                     // sentence
                                                                     // (boundary
                                                                     // case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing
                                                                                 // spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    // ---------------- Tests for getDetails
    // --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetailsExceptionGiven() {
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetailsNullGivenAssertionError() {
        thrown.expect(AssertionError.class);
        StringUtil.getDetails(null);
    }

    //@@author A0121668A
    // ---------------- Tests for parseStringToTime --------------------------------------

    /*
     * Invalid equivalence partitions for time: nullß
     */

    private void assertTimeExceptionThrown(String timeString, String errorMessage) {
        thrown.expectMessage(errorMessage);
        thrown.expect(DateTimeException.class);
        StringUtil.parseStringToTime(timeString);
    }

    private void assertTimeFormatExceptionThrown(String timeString) {
        thrown.expect(DateTimeParseException.class);
        StringUtil.parseStringToTime(timeString);
    }

    @Test
    public void parseStringToTimeNullTimeStringExceptionThrown() {
        assertTimeExceptionThrown(null, StringUtil.TIME_FORMAT_CONSTRAINTS);
    }

    @Test
    public void parseStringToTimeWrongFormatexceptionThrown() {
        assertTimeFormatExceptionThrown(""); //eactuallmpty Time String
        assertTimeFormatExceptionThrown("a"); //an alphabet
        assertTimeFormatExceptionThrown("word"); //a string
    }

    // ---------------- Tests for parseStringToDate --------------------------------------

    /*
     * Invalid equivalence partitions for date: null
     */
    private void assertDateExceptionThrown(String dateString, String errorMessage) {
        thrown.expectMessage(errorMessage);
        thrown.expect(DateTimeException.class);
        StringUtil.parseStringToDate(dateString);
    }

    private void assertDateFormatExceptionThrown(String timeString) {
        thrown.expect(DateTimeParseException.class);
        StringUtil.parseStringToDate(timeString);
    }

    @Test
    public void parseStringToDateNullDateStringExceptionThrown() {
        assertDateExceptionThrown(null, StringUtil.DATE_FORMAT_CONSTRAINTS);
    }

    @Test
    public void parseStringToDateWrongFormatexceptionThrown() {
        assertDateFormatExceptionThrown(""); //empty Time String
        assertDateFormatExceptionThrown("a"); //an alphabet
        assertDateFormatExceptionThrown("word"); //a String
    }
}
