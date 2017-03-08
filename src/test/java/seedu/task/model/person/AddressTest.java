package seedu.task.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.CompletionStatus;

public class AddressTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(CompletionStatus.isValidCompletionStatus("")); // empty string
        assertFalse(CompletionStatus.isValidCompletionStatus(" ")); // spaces only

        // valid addresses
        assertTrue(CompletionStatus.isValidCompletionStatus("Blk 456, Den Road, #01-355"));
        assertTrue(CompletionStatus.isValidCompletionStatus("-")); // one character
        assertTrue(CompletionStatus.isValidCompletionStatus("Leng Inc; 1234 Market St; "
                + "San Francisco CA 2349879; USA")); // long address
    }
}
