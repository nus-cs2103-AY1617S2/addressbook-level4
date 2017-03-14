package seedu.address.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.address.logic.autocomplete.Autocomplete;
import seedu.address.logic.autocomplete.AutocompleteTrie;

/**
 * Test class to test Autocomplete Class
 */
public class AutocompleteTrieTest {

    @Test
    public void setup() {
        Autocomplete ac = new AutocompleteTrie();
        assertTrue(ac.getSuggestions("").containsAll(Arrays.asList(AutocompleteTrie.AUTOCOMPLETE_DATA)));
    }

    @Test
    public void autocomplete_TestInitialization() {
        Autocomplete ac1 = new AutocompleteTrie("phrase1");
        ac1.addData("phrase2");
        Autocomplete ac2 = new AutocompleteTrie("phrase1", "phrase2");
        assertTrue(ac1.equals(ac2));
        assertFalse(ac1.equals(null));
    }
}
