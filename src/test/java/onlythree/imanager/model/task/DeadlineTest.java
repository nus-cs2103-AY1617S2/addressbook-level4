package onlythree.imanager.model.task;

import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import onlythree.imanager.model.task.exceptions.PastDateTimeException;
import onlythree.imanager.testutil.TestDateTimeHelper;

//@@author A0140023E
public class DeadlineTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /*
     * Invalid equivalence partitions for constructor: null, null and date-time in past allowed,
     *                                                 date-time in past and not allowed
     * Valid EP for constructor: date-time in past and allowed, current date-time, future date-time
     */
    @Test
    public void constructorPastDateTimeNotAllowed_null_expectsAssertionError() throws PastDateTimeException {
        exception.expect(AssertionError.class);
        new Deadline(null);
    }

    @Test
    public void constructorPastDateTimeAllowed_null_expectsAssertionError() throws PastDateTimeException {
        exception.expect(AssertionError.class);
        new Deadline(null, true);
    }

    /*
     * Boundary values for past date-time: 1 nanoseconds before
     *                                     Random past date-time
     *                                     Current date-time (not tested because time moves)
     *                                     1 minute after
     * (note that we cannot use one nanosecond later because it takes time for the deadline to construct)
     */
    @Test
    public void constructorPastDateTimeNotAllowed_dateTimeOneNanoBefore_expectsException()
            throws PastDateTimeException {
        expectPastDateTimeExceptionThrown();
        new Deadline(TestDateTimeHelper.getOneNanoBeforeCurrentDateTime());
    }

    @Test
    public void constructorPastDateTimeNotAllowed_somePastDateTime_expectsException()
            throws PastDateTimeException {
        expectPastDateTimeExceptionThrown();
        new Deadline(TestDateTimeHelper.getDaysBeforeCurrentDateTime(1));
    }

    @Test
    public void constructorPastDateTimeNotAllowed_dateTimeOneMinLater_success()
            throws PastDateTimeException {
        Deadline deadline = new Deadline(TestDateTimeHelper.getMinutesAfterCurrentDateTime(1));
        assertNotNull(deadline);
    }

    /*
     * Boundary values for past date-time and allowed : 1 nanoseconds before
     *                                                  Random past date-time
     *                                                  Current date-time (not tested because time moves)
     *                                                  1 minute after
     * (note that we cannot use one nanosecond later because it takes time for the deadline to construct)
     */
    @Test
    public void constructorPastDateTimeAllowed_dateTimeOneNanoBefore_success()
            throws PastDateTimeException {
        Deadline deadline = new Deadline(TestDateTimeHelper.getOneNanoBeforeCurrentDateTime(), true);
        assertNotNull(deadline);
    }

    @Test
    public void constructorPastDateTimeAllowed_somePastDateTime_success()
            throws PastDateTimeException {
        Deadline deadline = new Deadline(TestDateTimeHelper.getDaysBeforeCurrentDateTime(1), true);
        assertNotNull(deadline);
    }

    @Test
    public void constructorPastDateTimeAllowed_dateTimeOneNanoLater_success()
            throws PastDateTimeException {
        Deadline deadline = new Deadline(TestDateTimeHelper.getMinutesAfterCurrentDateTime(1), true);
        assertNotNull(deadline);
    }

    // some date-time in future and past date-time not allowed
    @Test
    public void constructorPastDateTimeNotAllowed_someFutureDateTime_success() throws PastDateTimeException {
        Deadline deadline = new Deadline(TestDateTimeHelper.getDaysAfterCurrentDateTime(3));
        assertNotNull(deadline);
    }

    // some date-time in future and past date-time allowed
    @Test
    public void constructorPastDateTimeAllowed_someFutureDateTime_success() throws PastDateTimeException {
        Deadline deadline = new Deadline(TestDateTimeHelper.getDaysAfterCurrentDateTime(3), true);
        assertNotNull(deadline);
    }

    private void expectPastDateTimeExceptionThrown() {
        exception.expect(PastDateTimeException.class);
        exception.expectMessage(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
    }

}
