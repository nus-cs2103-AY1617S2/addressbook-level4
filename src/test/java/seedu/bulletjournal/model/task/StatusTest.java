//@@author A0105748B
package seedu.bulletjournal.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StatusTest {

    @Test
    public void isValidStatus() {
        // blank status
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only

        // missing parts
        assertFalse(Status.isValidStatus("@webmail.com")); // not specific word
        assertFalse(Status.isValidStatus("peterjackwebmail.com")); // not specific word
        assertFalse(Status.isValidStatus("peterjack@")); // not specific word

        // invalid parts
        assertFalse(Status.isValidStatus("-@webmail.com")); // not specific word
        assertFalse(Status.isValidStatus("peterjack@-")); // not specific word
        assertFalse(Status.isValidStatus("peter jack@webmail.com")); // not specific word
        assertFalse(Status.isValidStatus("peterjack@web mail.com")); // not specific word
        assertFalse(Status.isValidStatus("peterjack@@webmail.com")); // not specific word
        assertFalse(Status.isValidStatus("peter@jack@webmail.com")); // not specific word
        assertFalse(Status.isValidStatus("peterjack@webmail@com")); // not specific word

        // valid status
        assertTrue(Status.isValidStatus("undone"));
        assertTrue(Status.isValidStatus("undone"));  // specific word
        assertTrue(Status.isValidStatus("done"));   // specific word
        assertTrue(Status.isValidStatus("done"));  // specific word
        assertTrue(Status.isValidStatus("done"));  // specific word
        assertTrue(Status.isValidStatus("undone"));    // specific word
        assertTrue(Status.isValidStatus("done"));   // specific word
        assertTrue(Status.isValidStatus("undone"));    // specific word
    }
}
