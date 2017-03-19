package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InformationTest {

    @Test
    public void isValidInformation() {
        // invalid information
        assertFalse(Information.isValidInformation("")); // empty string
        assertFalse(Information.isValidInformation(" ")); // spaces only

        // valid information
        assertTrue(Information.isValidInformation("Finish the project"));
        assertTrue(Information.isValidInformation("Discussion with team members at SOC"));
    }
}
