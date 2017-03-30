package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TitleTest {

    @Test
    public void isValidTitle() {
        // invalid name
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidTitle("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidTitle("peter jack")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("peter the 2nd")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Capital Tan")); // with capital letters
        assertTrue(Title.isValidTitle("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
