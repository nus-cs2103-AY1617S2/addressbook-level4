package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TitleTest {

    // @@author A0114269E
    @Test
    public void isValidTitle() {
        // invalid name
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidTitle("peter^")); // contains not allowable non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidTitle("borrow mouse")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("buy a 2nd car")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Visit NUS")); // with capital letters
        assertTrue(Title.isValidTitle("Meeting with David Roger Jackson Ray Jr 2nd")); // long title
//        assertTrue(Title.isValidTitle("Meet with peter!")); // contains allowable non-alphanumeric characters
    }
    // @@author
}
