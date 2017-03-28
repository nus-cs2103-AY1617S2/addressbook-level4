package seedu.tasklist.commons.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

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
        assertFalse(StringUtil.isUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isUnsignedInteger("1 0"));  // Spaces in the middle

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isUnsignedInteger("10"));
    }

//@@author A0139221N
    //---------------- Tests for containsStartingLettersIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsStartingLettersIgnoreCase_nullWord_exceptionThrown() {
        assertExceptionThrown("typical sentence", null, "WordSet parameter cannot be null");
    }

    private void assertExceptionThrown(String sentence, Set<String> wordSet, String errorMessage) {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(errorMessage);
        StringUtil.containsStartingLettersIgnoreCase(sentence, wordSet);
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_exceptionThrown() {
        Set<String> wordSet = new HashSet<>();
        assertExceptionThrown("typical sentence", wordSet, "WordSet parameter cannot be empty");
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_exceptionThrown() {
        Set<String> wordSet = new HashSet<>();
        wordSet.add("not typical word");
        assertExceptionThrown("typical sentence", wordSet, "Word in wordSet should be a single word");
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_exceptionThrown() {
        Set<String> wordSet = new HashSet<>();
        wordSet.add("typical");
        wordSet.add("word");
        assertExceptionThrown(null, wordSet, "Sentence parameter cannot be null");
    }

    /*
     * Valid equivalence partitions for wordSet:
     *   - any word
     *   - any number of words
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
     *   - sentence words only match some of the query words
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() {
        Set<String> wordSet = new HashSet<>();
        wordSet.add("abc");

        // Empty sentence
        assertFalse(StringUtil.containsStartingLettersIgnoreCase("", wordSet)); // Boundary case
        assertFalse(StringUtil.containsStartingLettersIgnoreCase("    ", wordSet));

        // Matches a partial word only
        wordSet = new HashSet<>();
        wordSet.add("bbbb");
        // Query word bigger than sentence word
        assertFalse(StringUtil.containsStartingLettersIgnoreCase("aaa bbb ccc", wordSet));

        wordSet.add("aaa");
        wordSet.add("ddd");
        // Matches only some of the words in the set
        assertFalse(StringUtil.containsStartingLettersIgnoreCase("aaa bbbb ccc", wordSet));

        wordSet = new HashSet<>();
        wordSet.add("bc");
        // Matches other parts of the word other than the staring letters
        assertFalse(StringUtil.containsStartingLettersIgnoreCase("abc dsd", wordSet));

        // Matches word in the sentence, different upper/lower case letters
        wordSet = new HashSet<>();
        wordSet.add("Bbb");
        // First word (boundary case)
        assertTrue(StringUtil.containsStartingLettersIgnoreCase("aaa bBb ccc", wordSet));

        wordSet = new HashSet<>();
        wordSet.add("CCc@1");
        // Last word (boundary case)
        assertTrue(StringUtil.containsStartingLettersIgnoreCase("aaa bBb ccc@1", wordSet));

        wordSet = new HashSet<>();
        wordSet.add("aaa");
        // Sentence has extra spaces
        assertTrue(StringUtil.containsStartingLettersIgnoreCase("  AAA   bBb   ccc  ", wordSet));
        // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsStartingLettersIgnoreCase("Aaa", wordSet));

        wordSet = new HashSet<>();
        wordSet.add("  ccc  ");
        // Leading/trailing spaces
        assertTrue(StringUtil.containsStartingLettersIgnoreCase("aaa bbb ccc", wordSet));

        wordSet = new HashSet<>();
        wordSet.add("bbB");
        // Matches multiple words in sentence
        assertTrue(StringUtil.containsStartingLettersIgnoreCase("AAA bBb ccc  bbb", wordSet));

        wordSet.add("aaa");
        // Matches multiple words in wordSet
        assertTrue(StringUtil.containsStartingLettersIgnoreCase("AAA bBb ccc", wordSet));

        wordSet = new HashSet<>();
        wordSet.add("ab");
        wordSet.add("b");
        wordSet.add("c1");
        // Matches starting letter of the word in sentence
        assertTrue(StringUtil.containsStartingLettersIgnoreCase("abc bbb c1", wordSet));
    }
//@@author

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

    //--------------- Tests for removeSquareBrackets -----------------------------

    @Test
    public void removeSquareBrackets() {
        //normal tags
        String testTag = "[test]";
        String expected = "test";
        assertEqual(StringUtil.removeSquareBrackets(testTag), expected);
        //number tags
        testTag = "[5]";
        expected = "5";
        assertEqual(StringUtil.removeSquareBrackets(testTag), expected);
        //empty tag
        testTag = "[]";
        expected = "";
        assertEqual(StringUtil.removeSquareBrackets(testTag), expected);
        //normal string
        testTag = "normal";
        expected = testTag;
        assertEqual(StringUtil.removeSquareBrackets(testTag), expected);
        //only [
        testTag = "[head";
        expected = testTag;
        assertEqual(StringUtil.removeSquareBrackets(testTag), expected);
        //only ]
        testTag = "]";
        expected = testTag;
        assertEqual(StringUtil.removeSquareBrackets(testTag), expected);
    }

    private void assertEqual(String string1, String string2) {
        assertTrue(string1.equals(string2));
    }
}
