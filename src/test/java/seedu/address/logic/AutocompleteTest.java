package seedu.address.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Test class to test Autocomplete Class
 */
public class AutocompleteTest {

    @Test
    public void setup() {
        Autocomplete ac = new Autocomplete();
        assertTrue(ac.getSuggestions("").containsAll(Arrays.asList(Autocomplete.autocompleteData)));
    }

    @Test
    public void autocomplete_TestInitialization() {
        Autocomplete ac1 = new Autocomplete("phrase1");
        ac1.addData("phrase2");
        Autocomplete ac2 = new Autocomplete("phrase1", "phrase2");
        assertTrue(ac1.equals(ac2));
        assertFalse(ac1.equals(null));
    }
}
