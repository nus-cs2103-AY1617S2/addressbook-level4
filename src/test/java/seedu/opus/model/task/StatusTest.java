package seedu.opus.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StatusTest {
    //@@author A0124368A
    @Test
    public void isValidStatus() {
        // valid status
        assertTrue(Status.isValidStatus("incomplete"));
        assertTrue(Status.isValidStatus("complete"));

        // invalid status
        assertFalse(Status.isValidStatus("done"));
        assertFalse(Status.isValidStatus("completed"));
        assertFalse(Status.isValidStatus("finished"));
        assertFalse(Status.isValidStatus("1/2 done")); // alphanumeric
        assertFalse(Status.isValidStatus("pending"));
    }
    //@@author
}
