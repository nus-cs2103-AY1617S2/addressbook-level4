package seedu.taskboss.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InformationTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(Information.isValidInformation("")); // empty string
        assertFalse(Information.isValidInformation(" ")); // spaces only

        // valid addresses
        assertTrue(Information.isValidInformation("Blk 456, Den Road, #01-355"));
        assertTrue(Information.isValidInformation("-")); // one character
        assertTrue(Information.isValidInformation("Leng Inc; 1234 Market St;"
                + " San Francisco CA 2349879; USA")); // long
                                                                                                                    // information
    }
}
