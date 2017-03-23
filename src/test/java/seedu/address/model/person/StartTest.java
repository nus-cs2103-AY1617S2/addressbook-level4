package seedu.address.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StartTest {

    @Test
    public void isValidStart() {
        // blank start
        assertTrue(Start.isValidStart("")); // empty string
        assertTrue(Start.isValidStart("3 Mar")); // specific date
        assertTrue(Start.isValidStart("4 pm")); // specific time
        assertTrue(Start.isValidStart("tomorrow")); // general word
    }
}
