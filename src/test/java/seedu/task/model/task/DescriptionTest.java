package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.Description;

public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // invalid descriptions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription("Get the milk")); // Text only
        assertTrue(Description.isValidDescription("12345678")); // Numbers only
        assertTrue(Description.isValidDescription("ಠ_ಠ")); // Symbols only
        assertTrue(Description.isValidDescription("つ ◕_◕ ༽つ TAKE MY ENERGY ༼ つ ◕_◕ ༽つ")); // Text and symbols
        assertTrue(Description.isValidDescription("Call delivery at 91234567")); // Text and numbers
        assertTrue(Description.isValidDescription("Tweet @Abcdefg with hashtag #hijkl123")); // Mix of text, numbers and symbols
    }
}
