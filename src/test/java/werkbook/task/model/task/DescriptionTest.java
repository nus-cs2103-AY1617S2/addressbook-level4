package werkbook.task.model.task;

// import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // valid descriptions
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // spaces only
        assertTrue(Description.isValidDescription("Get the milk")); // Text only
        assertTrue(Description.isValidDescription("12345678")); // Numbers only
        assertTrue(Description.isValidDescription("à² _à² ")); // Symbols only
        assertTrue(Description.isValidDescription("ã�¤ â—•_â—• "
                + "à¼½ã�¤ TAKE MY ENERGY "
                + " à¼¼ ã�¤ â—•_â—• à¼½ã�¤")); // Text and symbols
        assertTrue(Description.isValidDescription("Call delivery at 91234567")); // Text and numbers
        assertTrue(Description.isValidDescription("Tweet @Abcdefg "
                + "with hashtag #hijkl123")); // Mix of text, numbers and symbols
    }
}
