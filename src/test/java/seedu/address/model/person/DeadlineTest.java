package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidDate() {
        // invalid addresses
        assertFalse(Deadline.isValidDate("")); // empty string
        assertFalse(Deadline.isValidDate(" ")); // spaces only
        assertFalse(Deadline.isValidDate("Blk 456, Den Road, #01-355"));
        assertFalse(Deadline.isValidDate("23")); // two characters only

        // valid addresses
        assertTrue(Deadline.isValidDate("11-12-2106")); // date
    }
}
