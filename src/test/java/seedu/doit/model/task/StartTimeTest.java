package seedu.doit.model.task;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.item.StartTime;

//@@author A0146809W

public class StartTimeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValid_StartTime() {
        // valid startTime
        assertTrue(StartTime.isValidStartTime("20/03/17 10:21"));
        assertTrue(StartTime.isValidStartTime("21/04/15 15:10"));
        assertTrue(StartTime.isValidStartTime("01/05/16 14:34"));
        assertTrue(StartTime.isValidStartTime("01/04/17 06:20"));
        assertTrue(StartTime.isValidStartTime("25/03/12 04:55"));
        assertTrue(StartTime.isValidStartTime("23/04/17 14:60"));
        assertTrue(StartTime.isValidStartTime("21/02/17 19:20"));
        assertTrue(StartTime.isValidStartTime("28/03/16 12:15"));
    }

    @Test
    public void isInvalid_StartTime() {
        // invalid startTime
        assertFalse(StartTime.isValidStartTime("99/99/99 10:21");
    }

    @Test
    public void invalidStart_IllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        StartTime one = new StartTime("asdf");
    }
}
