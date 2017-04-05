package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class DescriptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /*
     * Invalid equivalence partitions for Description: null, empty
     */

    @Test
    public void description_null_assertFalse() throws IllegalValueException {
        assertErrorThrown(null);
    }

    public void assertErrorThrown(String description) throws IllegalValueException {
        thrown.expect(AssertionError.class);
        Description description1 = new Description(description);
    }

    @Test
    public void description_empty_exceptionThrown() throws IllegalValueException {
        assertExceptionThrown(" ");
    }

    public void assertExceptionThrown(String description) throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage("Description can take any values, and it should not be blank");
        Description description2 = new Description(description);
    }

    @Test
    public void isValidDescription() {
        /*
         * Invalid equivalence partitions for isValidDescription:
         *  - Empty
         *  - Space(s)
         *  - words with starting white spaces
         */
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        assertFalse(Description.isValidDescription(" Hwangs for lunch")); // words with starting white spaces

        /*
         * Valid equivalence partitions for isValidDescription:
         *  - any word
         *  - numbers only
         *  - words containing symbols/numbers
         */
        assertTrue(Description.isValidDescription("test")); // one word; alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("cs2103 2nd test")); // words containing numbers
        assertTrue(Description.isValidDescription("Lunch at Andy's place")); // words containing symbols
    }
}
