package seedu.toluist.commons.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //---------------- Tests for isUnsignedPositiveInteger --------------------------------------

    @Test
    public void isUnsignedPositiveInteger() {

        // Equivalence partition: null
        assertFalse(StringUtil.isPositiveInteger(null));

        // EP: empty strings
        assertFalse(StringUtil.isPositiveInteger("")); // Boundary value
        assertFalse(StringUtil.isPositiveInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isPositiveInteger("a"));
        assertFalse(StringUtil.isPositiveInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isPositiveInteger("0"));

        // EP: signed numbers
        assertFalse(StringUtil.isPositiveInteger("-1"));
        assertFalse(StringUtil.isPositiveInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isPositiveInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isPositiveInteger("1 0"));  // Spaces in the middle

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isPositiveInteger("1")); // Boundary value
        assertTrue(StringUtil.isPositiveInteger("10"));
    }


    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_exceptionThrown() {
        assertExceptionThrown("typical sentence", null, "Word parameter cannot be null");
    }

    private void assertExceptionThrown(String sentence, String word, String errorMessage) {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(errorMessage);
        StringUtil.containsWordIgnoreCase(sentence, word);
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_exceptionThrown() {
        assertExceptionThrown("typical sentence", "  ", "Word parameter cannot be empty");
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_exceptionThrown() {
        assertExceptionThrown("typical sentence", "aaa BBB", "Word parameter should be a single word");
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_exceptionThrown() {
        assertExceptionThrown(null, "abc", "Sentence parameter cannot be null");
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                   containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_assertionError() {
        thrown.expect(AssertionError.class);
        StringUtil.getDetails(null);
    }

    //@@ author A0131125Y
    //---------------- Tests for replaceFirstWord ----------------------------------

    @Test
    public void replaceFirstWord_nullString() {
        assertEquals(StringUtil.replaceFirstWord(null, "a"), null);
    }

    @Test
    public void replaceFirstWord_nullReplacement() {
        assertEquals(StringUtil.replaceFirstWord("word", null), "word");
    }

    @Test
    public void replaceFirstWord_emptyString() {
        assertEquals(StringUtil.replaceFirstWord("", "r"), "");
    }

    @Test
    public void replaceFirstWord_emptyReplacement() {
        assertEquals(StringUtil.replaceFirstWord("word a", ""), " a");
    }

    @Test
    public void replaceFirstWord_nonEmptyString() {
        assertEquals(StringUtil.replaceFirstWord("a b", "c"), "c b");
    }

    //---------------- Tests for startsWithIgnoreCase ----------------------------------

    @Test
    public void startsWithIgnoreCase_nullString() {
        assertEquals(StringUtil.startsWithIgnoreCase(null, "a"), false);
    }

    @Test
    public void startsWithIgnoreCase_nullComparison() {
        assertEquals(StringUtil.startsWithIgnoreCase("word", null), false);
    }

    @Test
    public void startsWithIgnoreCase_emptyString() {
        assertEquals(StringUtil.startsWithIgnoreCase("", "r"), false);
    }

    @Test
    public void startsWithIgnoreCase_emptyComparison() {
        assertEquals(StringUtil.startsWithIgnoreCase("word a", ""), true);
    }

    @Test
    public void startsWithIgnoreCase_sameCase() {
        assertEquals(StringUtil.startsWithIgnoreCase("ab c ", "a"), true);
    }

    @Test
    public void startsWithIgnoreCase_differentCase() {
        assertEquals(StringUtil.startsWithIgnoreCase("ab c ", "A"), true);
    }

    //@@ author A0162011A
    //---------------- Tests for convertToArray --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void convertToArray_nullCondition() {
        String testString = null;
        String[] testArray = StringUtil.convertToArray(testString);
        assertTrue(testArray[0].equals(""));
    }

    @Test
    public void convertToArray_oneCondition() {
        String testString = "test";
        String[] testArray = StringUtil.convertToArray(testString);
        assertTrue(testArray[0].equals("test"));
    }

    @Test
    public void convertToArray_twoConditions() {
        String testString = "test1 test2";
        String[] testArray = StringUtil.convertToArray(testString);
        assertTrue(testArray[0].equals("test1"));
        assertTrue(testArray[1].equals("test2"));
    }

    @Test
    public void convertToArray_whiteSpaceInFront() {
        String testString = "              test";
        String[] testArray = StringUtil.convertToArray(testString);
        assertTrue(testArray[0].equals("test"));
    }

    @Test
    public void convertToArray_whiteSpaceBehind() {
        String testString = "test          ";
        String[] testArray = StringUtil.convertToArray(testString);
        assertTrue(testArray[0].equals("test"));
    }

    @Test
    public void convertToArray_whiteSpaceInbetween() {
        String testString = "test1              test2";
        String[] testArray = StringUtil.convertToArray(testString);
        assertTrue(testArray[0].equals("test1"));
        assertTrue(testArray[1].equals("test2"));
    }

    @Test
    public void convertToArray_whiteSpaceEverywhere() {
        String testString = "          test1              test2            ";
        String[] testArray = StringUtil.convertToArray(testString);
        assertTrue(testArray[0].equals("test1"));
        assertTrue(testArray[1].equals("test2"));
    }
}
