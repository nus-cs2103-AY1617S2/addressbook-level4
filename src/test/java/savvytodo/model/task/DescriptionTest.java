package savvytodo.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // blank email
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // spaces only
        assertTrue(Description.isValidDescription("Some optional description")); // some description
    }
}
