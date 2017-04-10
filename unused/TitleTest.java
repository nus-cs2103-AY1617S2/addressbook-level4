// @@author A0141011J
package seedu.taskit.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskit.model.task.Title;

public class TitleTest {

    @Test
    public void isValidTitle() {
        // invalid name
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidTitle("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidTitle("meeting")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("CS2103 Homework")); // alphanumeric characters
        assertTrue(Title.isValidTitle("SE Meeting")); // with capital letters
        assertTrue(Title.isValidTitle("Interview for summer internship at NUS")); // long title
    }
}

