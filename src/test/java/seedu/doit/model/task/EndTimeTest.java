package seedu.doit.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.item.EndTime;

public class EndTimeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidEndTime() {
        // valid deadline
        assertTrue(EndTime.isValidEndTime("3/20/17 10:21"));
        assertTrue(EndTime.isValidEndTime("4/21/15 15:10"));
        assertTrue(EndTime.isValidEndTime("5/01/16 14:34"));
        assertTrue(EndTime.isValidEndTime("4/01/17 06:20"));
        assertTrue(EndTime.isValidEndTime("3/25/12 04:55"));
        assertTrue(EndTime.isValidEndTime("4/23/17 14:60"));
        assertTrue(EndTime.isValidEndTime("2/21/17 19:20"));
        assertTrue(EndTime.isValidEndTime("3/28/16 12:15"));
    }

    @Test
    public void invalidDate_IllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        EndTime one = new EndTime("asdf");
    }
}
