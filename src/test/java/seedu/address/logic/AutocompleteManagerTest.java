package seedu.address.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

import seedu.address.logic.autocomplete.AutocompleteManager;
import seedu.address.logic.autocomplete.AutocompleteRequest;
import seedu.address.logic.autocomplete.AutocompleteResponse;

//@@author A0140042A
/**
 * Test class to test AutocompleteManager Class
 */
public class AutocompleteManagerTest {

    @Test
    public void setup() {
        AutocompleteManager ac = new AutocompleteManager();
        AutocompleteRequest request = new AutocompleteRequest("", 0);
        assertTrue(ac.getSuggestions(request).getSuggestions().
                    containsAll(Arrays.asList(AutocompleteManager.AUTOCOMPLETE_DATA)));
    }

    @Test
    public void autocomplete_TestInitialization() {
        AutocompleteManager ac1 = new AutocompleteManager("phrase1");
        ac1.addData("phrase2");
        AutocompleteManager ac2 = new AutocompleteManager("phrase1", "phrase2");
        assertTrue(ac1.equals(ac2));
        assertFalse(ac1.equals(null));
    }

    @Test
    public void autocomplete_TestAutocompleteSuggestion() {
        AutocompleteManager ac = new AutocompleteManager();
        AutocompleteRequest request = new AutocompleteRequest("edi", 0);
        LinkedList<String> suggestions = new LinkedList<String>();
        suggestions.add("edit");
        suggestions.add("editlabel");
        AutocompleteResponse response = new AutocompleteResponse("edit", 4, suggestions);
        assertTrue(ac.getSuggestions(request).equals(response));
    }
}
