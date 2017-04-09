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
        assertTrue(EndTime.isValidEndTime("20/03/17 10:21"));
        assertTrue(EndTime.isValidEndTime("21/04/15 15:10"));
        assertTrue(EndTime.isValidEndTime("01/05/16 14:34"));
        assertTrue(EndTime.isValidEndTime("01/04/17 06:20"));
        assertTrue(EndTime.isValidEndTime("25/03/12 04:55"));
        assertTrue(EndTime.isValidEndTime("23/04/17 14:60"));
        assertTrue(EndTime.isValidEndTime("21/02/17 19:20"));
        assertTrue(EndTime.isValidEndTime("28/03/16 12:15"));
    }

    @Test
    public void invalidDate_IllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        EndTime one = new EndTime("asdf");
    }
}
