package seedu.doit.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.item.StartTime;

public class StartTimeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidStartTime() {
        // valid startTime
        assertTrue(StartTime.isValidStartTime("3/20/17 10:21"));
        assertTrue(StartTime.isValidStartTime("4/21/15 15:10"));
        assertTrue(StartTime.isValidStartTime("5/01/16 14:34"));
        assertTrue(StartTime.isValidStartTime("4/01/17 06:20"));
        assertTrue(StartTime.isValidStartTime("3/25/12 04:55"));
        assertTrue(StartTime.isValidStartTime("4/23/17 14:60"));
        assertTrue(StartTime.isValidStartTime("2/21/17 19:20"));
        assertTrue(StartTime.isValidStartTime("3/28/16 12:15"));
    }

    @Test
    public void invalidStart_IllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        StartTime one = new StartTime("asdf");
    }
}
