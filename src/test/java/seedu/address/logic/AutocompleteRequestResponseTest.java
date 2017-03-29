package seedu.address.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import seedu.address.logic.autocomplete.AutocompleteRequest;
import seedu.address.logic.autocomplete.AutocompleteResponse;

//@@author A0140042A
/**
 * Test cases to check if autocomplete works at the cursor position
 */
public class AutocompleteRequestResponseTest {

    @Test
    public void request_TestNegativeCaretPosition() {
        try {
            new AutocompleteRequest("", -1);
        } catch (AssertionError e) {
            assertTrue(null == e.getMessage());
        }
    }

    @Test
    public void request_TestEquals() {
        AutocompleteRequest request1 = new AutocompleteRequest("HelloWorld", 2);
        AutocompleteRequest request2 = new AutocompleteRequest("HelloWorld", 2);
        AutocompleteRequest request3 = new AutocompleteRequest("HelloWorld", 3);
        AutocompleteRequest request4 = new AutocompleteRequest("Hello", 1);
        AutocompleteRequest request5 = new AutocompleteRequest("Hello", 2);
        assertTrue(request1.equals(request2));
        assertFalse(request1.equals(request3));
        assertFalse(request1.equals(request4));
        assertFalse(request1.equals(request5));
        assertFalse(request1.equals(null));
    }

    @Test
    public void response_TestEquals() {
        LinkedList<String> suggestions1 = new LinkedList<String>();
        suggestions1.add("suggestion1");
        suggestions1.add("suggestion2");
        LinkedList<String> suggestions2 = new LinkedList<String>();
        suggestions2.add("suggestion1");

        AutocompleteResponse response1 = new AutocompleteResponse("HelloWorld", 2, null);
        AutocompleteResponse response2 = new AutocompleteResponse("HelloWorld", 2, null);
        AutocompleteResponse response3 = new AutocompleteResponse("HelloWorld", 3, null);
        AutocompleteResponse response4 = new AutocompleteResponse("Hello", 1, suggestions1);
        AutocompleteResponse response5 = new AutocompleteResponse("Hello", 2, null);
        AutocompleteResponse response6 = new AutocompleteResponse("Hello", 1, suggestions1);
        AutocompleteResponse response7 = new AutocompleteResponse("Hello", 1, suggestions2);
        AutocompleteResponse response8 = new AutocompleteResponse("Hello", 1, null);
        assertTrue(response4.equals(response6));
        assertTrue(response4.equals(response7));
        assertFalse(response4.equals(response8));
        assertTrue(response1.equals(response2));
        assertFalse(response1.equals(response3));
        assertFalse(response1.equals(response4));
        assertFalse(response1.equals(response5));
        assertFalse(response1.equals(null));
    }
}
