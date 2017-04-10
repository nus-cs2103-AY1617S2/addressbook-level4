package onlythree.imanager.model.task;

import static org.junit.Assert.assertNotNull;

import java.time.ZonedDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import onlythree.imanager.model.task.exceptions.InvalidDurationException;
import onlythree.imanager.model.task.exceptions.PastDateTimeException;
import onlythree.imanager.testutil.TestDateTimeHelper;

//@@author A0140023E
public class StartEndDateTimeTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /*
     * Past date times not allowed EP: [past start date-time]
     *                                 [past end date-time]
     *                                 [past start and end date-time]
     *                                 [future start and end date-time]
     */
    @Test
    public void constructorPastDateTimeNotAllowed_pastStartDateTime_expectsException()
            throws PastDateTimeException, InvalidDurationException {
        expectPastStartDateTimeExceptionThrown();
        ZonedDateTime startDateTime = TestDateTimeHelper.getOneNanoBeforeCurrentDateTime();
        ZonedDateTime endDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(4);
        new StartEndDateTime(startDateTime, endDateTime);
    }

    @Test
    public void constructorPastDateTimeNotAllowed_pastEndDateTime_expectsException()
            throws PastDateTimeException, InvalidDurationException {
        expectPastEndDateTimeExceptionThrown();
        ZonedDateTime startDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(2);
        ZonedDateTime endDateTime = TestDateTimeHelper.getOneNanoBeforeCurrentDateTime();
        new StartEndDateTime(startDateTime, endDateTime);
    }

    @Test
    public void constructorPastDateTimeNotAllowed_pastStartEndDateTime_expectsException()
            throws PastDateTimeException, InvalidDurationException {
        expectPastStartDateTimeExceptionThrown();
        ZonedDateTime startDateTime = TestDateTimeHelper.getOneNanoBeforeCurrentDateTime();
        ZonedDateTime endDateTime = TestDateTimeHelper.getOneNanoBeforeCurrentDateTime();
        new StartEndDateTime(startDateTime, endDateTime);
    }

    @Test
    public void constructorPastDateTimeNotAllowed_futureStartEndDateTime_success()
            throws PastDateTimeException, InvalidDurationException {
        ZonedDateTime startDateTime = TestDateTimeHelper.getMinutesAfterCurrentDateTime(3);
        ZonedDateTime endDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(2);
        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime);
        assertNotNull(startEndDateTime);
    }

    /*
     * Past date times allowed EP: [past start date-time]
     *                                 [past end date-time]
     *                                 [past start and end date-time]
     *                                 [future start and end date-time]
     */

    @Test
    public void constructorPastDateTimeAllowed_pastStartDateTime_success()
            throws PastDateTimeException, InvalidDurationException {
        ZonedDateTime startDateTime = TestDateTimeHelper.getOneNanoBeforeCurrentDateTime();
        ZonedDateTime endDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(1);
        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime, true);
        assertNotNull(startEndDateTime);
    }

    @Test
    public void constructorPastDateTimeAllowed_pastEndDateTime_exceptionThrown()
            throws PastDateTimeException, InvalidDurationException {
        expectInvalidDurationExceptionExceptionThrown();
        ZonedDateTime startDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(3);
        ZonedDateTime endDateTime = TestDateTimeHelper.getOneNanoBeforeCurrentDateTime();
        new StartEndDateTime(startDateTime, endDateTime, true);
    }

    @Test
    public void constructorPastDateTimeAllowed_pastStartEndDateTime_success()
            throws PastDateTimeException, InvalidDurationException {
        ZonedDateTime startDateTime = TestDateTimeHelper.getDaysBeforeCurrentDateTime(6);
        ZonedDateTime endDateTime = TestDateTimeHelper.getDaysBeforeCurrentDateTime(3);
        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime, true);
        assertNotNull(startEndDateTime);
    }

    @Test
    public void constructorPastDateTimeAllowed_futureStartEndDateTime_success()
            throws PastDateTimeException, InvalidDurationException {
        ZonedDateTime startDateTime = TestDateTimeHelper.getMinutesAfterCurrentDateTime(3);
        ZonedDateTime endDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(7);
        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime, true);
        assertNotNull(startEndDateTime);
    }

    /*
     * Past date times allowed and invalid duration EP: [past start date-time before end date-time]
     *                                                  [past start date-time equal end date-time]
     *                                                  [past start date-time after end date-time]
     *                                                  [past end date-time before start date-time]
     *                                                  [past end date-time equal start date-time]
     *                                                  [past end date-time after start date-time]
     */
    @Test
    public void constructorPastDateTimeAllowed_pastStartDateTimeBeforeEndDateTime_success()
            throws PastDateTimeException, InvalidDurationException {
        ZonedDateTime startDateTime = TestDateTimeHelper.getOneNanoBeforeCurrentDateTime();
        ZonedDateTime endDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(2);
        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime, true);
        assertNotNull(startEndDateTime);
    }

    // side effect, the end date-time is also in the past
    @Test
    public void constructorPastDateTimeAllowed_pastStartDateTimeEqualEndDateTime_expectsException()
            throws PastDateTimeException, InvalidDurationException {
        expectInvalidDurationExceptionExceptionThrown();
        ZonedDateTime startDateTime = TestDateTimeHelper.getOneNanoBeforeCurrentDateTime();
        ZonedDateTime endDateTime = startDateTime;
        new StartEndDateTime(startDateTime, endDateTime, true);
    }

    @Test
    public void constructorPastDateTimeAllowed_pastStartDateTimeAfterEndDateTime_expectsException()
            throws PastDateTimeException, InvalidDurationException {
        expectInvalidDurationExceptionExceptionThrown();
        ZonedDateTime startDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(3);
        ZonedDateTime endDateTime = TestDateTimeHelper.getMinutesAfterCurrentDateTime(2);
        new StartEndDateTime(startDateTime, endDateTime, true);
    }

    @Test
    public void constructorPastDateTimeAllowed_pastEndDateTimeBeforeStartDateTime_expectsException()
            throws PastDateTimeException, InvalidDurationException {
        expectInvalidDurationExceptionExceptionThrown();
        ZonedDateTime startDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(3);
        ZonedDateTime endDateTime = TestDateTimeHelper.getOneNanoBeforeCurrentDateTime();
        new StartEndDateTime(startDateTime, endDateTime, true);
    }

    // notice this is essentially equal to the below. Different date-time is used however
    // constructorPastDateTimeAllowed_pastStartDateTimeEqualEndDateTime_expectsException()
    @Test
    public void constructorPastDateTimeAllowed_pastEndDateTimeEqualStartDateTime_expectsException()
            throws PastDateTimeException, InvalidDurationException {
        expectInvalidDurationExceptionExceptionThrown();
        ZonedDateTime endDateTime = TestDateTimeHelper.getDaysBeforeCurrentDateTime(5);
        ZonedDateTime startDateTime = endDateTime;
        new StartEndDateTime(startDateTime, endDateTime, true);
    }

    @Test
    public void constructorPastDateTimeAllowed_pastEndDateTimeAfterStartDateTime_success()
            throws PastDateTimeException, InvalidDurationException {
        ZonedDateTime startDateTime = TestDateTimeHelper.getDaysBeforeCurrentDateTime(3);
        ZonedDateTime endDateTime = TestDateTimeHelper.getDaysBeforeCurrentDateTime(2);
        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime, true);
        assertNotNull(startEndDateTime);
    }

    /*
     * Future date times with invalid duration EP: [future start date-time before end date-time]
     * and past date times not allowed             [future start date-time equal end date-time]
     *                                             [future start date-time after end date-time]
     *                                             [future end date-time before start date-time]
     *                                             [future end date-time equal start date-time]
     *                                             [future end date-time after start date-time]
     */
    @Test
    public void constructorPastDateTimeNotAllowed_futureStartDateTimeBeforeEndDateTime_success()
            throws PastDateTimeException, InvalidDurationException {
        ZonedDateTime startDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(4);
        ZonedDateTime endDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(6);
        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime);
        assertNotNull(startEndDateTime);
    }

    @Test
    public void constructorPastDateTimeNotAllowed_futureStartDateTimeEqualEndDateTime_expectsException()
            throws PastDateTimeException, InvalidDurationException {
        expectInvalidDurationExceptionExceptionThrown();
        ZonedDateTime endDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(7);
        ZonedDateTime startDateTime = endDateTime;
        new StartEndDateTime(startDateTime, endDateTime);
    }

    @Test
    public void constructorPastDateTimeNotAllowed_futureStartDateTimeAfterEndDateTime_expectsException()
            throws PastDateTimeException, InvalidDurationException {
        expectInvalidDurationExceptionExceptionThrown();
        ZonedDateTime endDateTime = TestDateTimeHelper.getMinutesAfterCurrentDateTime(2);
        ZonedDateTime startDateTime = endDateTime.plusSeconds(7);
        new StartEndDateTime(startDateTime, endDateTime);
    }

    // effectively equals to
    // constructorPastDateTimeNotAllowed_futureStartDateTimeAfterEndDateTime_expectsException()
    @Test
    public void constructorPastDateTimeNotAllowed_futureEndDateTimeBeforeStartDateTime_expectsException()
            throws PastDateTimeException, InvalidDurationException {
        expectInvalidDurationExceptionExceptionThrown();
        ZonedDateTime startDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(9);
        ZonedDateTime endDateTime = startDateTime.minusHours(3);
        new StartEndDateTime(startDateTime, endDateTime);
    }

    // effectively equals to
    // constructorPastDateTimeNotAllowed_futureStartDateTimeEqualEndDateTime_expectsException()
    @Test
    public void constructorPastDateTimeNotAllowed_futureEndDateTimeEqualStartDateTime_expectsException()
            throws PastDateTimeException, InvalidDurationException {
        expectInvalidDurationExceptionExceptionThrown();
        ZonedDateTime startDateTime = TestDateTimeHelper.getMinutesAfterCurrentDateTime(3);
        ZonedDateTime endDateTime = startDateTime;
        new StartEndDateTime(startDateTime, endDateTime);
    }
    // effectively equals to
    // constructorPastDateTimeNotAllowed_futureStartDateTimeBeforeEndDateTime_success()
    @Test
    public void constructorPastDateTimeNotAllowed_futureEndDateTimeAfterStartDateTime_success()
            throws PastDateTimeException, InvalidDurationException {
        ZonedDateTime startDateTime = TestDateTimeHelper.getDaysAfterCurrentDateTime(9);
        ZonedDateTime endDateTime = startDateTime.plusSeconds(3);
        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime);
        assertNotNull(startEndDateTime);
    }

    /*
     * Future date times with invalid duration EP and past date-times allowed not tested
     * Future date times with valid duration EP and past date-times allowed not tested
     */

    private void expectPastStartDateTimeExceptionThrown() {
        exception.expect(PastDateTimeException.class);
        exception.expectMessage(StartEndDateTime.MESSAGE_PAST_START_DATETIME_CONSTRAINTS);
    }

    private void expectPastEndDateTimeExceptionThrown() {
        exception.expect(PastDateTimeException.class);
        exception.expectMessage(StartEndDateTime.MESSAGE_PAST_END_DATETIME_CONSTRAINTS);
    }

    private void expectInvalidDurationExceptionExceptionThrown() {
        exception.expect(InvalidDurationException.class);
        exception.expectMessage(StartEndDateTime.MESSAGE_INVALID_DURATION_CONSTRAINTS);
    }

}
