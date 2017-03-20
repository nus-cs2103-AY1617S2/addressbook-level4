package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StartTimeTest {

    @Test
    public void isValidStartTime() {
        // blank email
        assertFalse(StartTime.isValidStartTime("")); // empty string
        

        // valid email
        assertTrue(StartTime.isValidStartTime("march fifteenth")); //alphabets
        assertTrue(StartTime.isValidStartTime("20170315")); // number
        assertTrue(StartTime.isValidStartTime("By end of March")); // alphabets with capital
        assertTrue(StartTime.isValidStartTime("March 15 2017")); // numeric and alphabet
                                                           // and domain name
        assertTrue(StartTime.isValidStartTime("March 15, 2017")); // mixture of
                                                               // alphanumeric
                                                               // and comma
                                                               // characters
    }
}
