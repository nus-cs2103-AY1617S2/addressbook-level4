package seedu.todolist.commons.util;

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

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.tag.Tag;

public class StringUtilTest {
    Set<Tag> tags;

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


    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_exceptionThrown() throws IllegalValueException {
        Tag tag = new Tag("tag1");
        tags = new HashSet<Tag> ();
        tags.add(tag);
        assertExceptionThrown("typical sentence", tags, null, "Word parameter cannot be null");
    }

    private void assertExceptionThrown(String sentence, Set<Tag> tags, String word, String errorMessage) {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(errorMessage);
        StringUtil.containsIgnoreCase(sentence, tags, word);
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_exceptionThrown()  throws IllegalValueException {
        Tag tag = new Tag("tag1");
        tags = new HashSet<Tag> ();
        tags.add(tag);
        assertExceptionThrown("typical sentence", tags, "  ", "Word parameter cannot be empty");
    }

   // @Test
   // public void containsWordIgnoreCase_multipleWords_exceptionThrown() {
   //     assertExceptionThrown("typical sentence", "aaa BBB", "Word parameter should be a single word");
   // }

    @Test
    public void containsWordIgnoreCase_nullSentence_exceptionThrown() throws IllegalValueException {
        Tag tag = new Tag("tag1");
        tags = new HashSet<Tag> ();
        tags.add(tag);
        assertExceptionThrown(null, tags,  "abc", "Sentence parameter cannot be null");
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
    public void containsWordIgnoreCase_validInputs_correctResult() throws IllegalValueException {
        Tag tag = new Tag("tag1");
        tags = new HashSet<Tag> ();
        tags.add(tag);
        // Empty sentence
        assertFalse(StringUtil.containsIgnoreCase("", tags, "abc")); // Boundary case
        assertFalse(StringUtil.containsIgnoreCase("    ", tags, "123"));

        // Matches a partial word only
        assertTrue(StringUtil.containsIgnoreCase("aaa bbb ccc", tags, "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsIgnoreCase("aaa bbb ccc", tags, "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsIgnoreCase("aaa bBb ccc", tags, "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsIgnoreCase("aaa bBb ccc@1", tags, "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsIgnoreCase("  AAA   bBb   ccc  ", tags, "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsIgnoreCase("Aaa", tags, "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsIgnoreCase("aaa bbb ccc", tags, "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsIgnoreCase("AAA bBb ccc  bbb", tags, "bbB"));
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


}
