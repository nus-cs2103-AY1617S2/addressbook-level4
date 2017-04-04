package seedu.tache.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("swimming*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("go to the gym")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("home alone 3")); // alphanumeric characters
        assertTrue(Name.isValidName("Dr Wong")); // with capital letters
        assertTrue(Name.isValidName("Research on the Meaning of Life")); // long names
    }
}
