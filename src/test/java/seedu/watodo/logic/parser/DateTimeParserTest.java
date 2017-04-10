package seedu.watodo.logic.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.TaskType;

//@@author A0143076J
public class DateTimeParserTest {

    private DateTimeParser dateTimeParser = new DateTimeParser();
    private final String validStartDate = "wed";
    private final String validEndDate = "10 apr 5pm";
    private final String invalidDate = "invalid Date";

    /*
     * Valid equivalence partitions for String args: no DateTime prefixes, one
     * StartDateTime prefix, one start and one end DateTime prefix Each valid
     * combination is tested one at a time below.
     */
    @Test
    public void parse_argWithValidCombiOfPrefixes() throws IllegalValueException {
        String a = "some string without any dateTime prefix";
        String b = "some string with by/" + validEndDate;
        String c = "some string with on/" + validEndDate;
        String d = "some string with from/ " + validStartDate + " to/ " + validEndDate;
        String e = "some string with on/ " + validStartDate + " to/ " + validEndDate;

        assertTaskTypeMatches(a, TaskType.FLOAT);
        assertTaskTypeMatches(b, TaskType.DEADLINE);
        assertTaskTypeMatches(c, TaskType.DEADLINE);
        assertTaskTypeMatches(d, TaskType.EVENT);
        assertTaskTypeMatches(e, TaskType.EVENT);
    }

    private void assertTaskTypeMatches(String args, TaskType type) throws IllegalValueException {
        dateTimeParser.parse(args);
        assertEquals(dateTimeParser.getTaskType(), type);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /*
     * Invalid equivalence partitions String args: null, same prefix repeated,
     * start prefix but no end prefix.
     */
    @Test
    public void parse_argWithInvalidCombiOfPrefixes_exceptionThrown() throws IllegalValueException {
        prefixThrowException("invalid string with repeated by/ some date by/ prefixes");
        prefixThrowException("invalid string with by/ deadline prefix and extra to/ enddate prefixes");
        prefixThrowException("invalid string with from/ startdate prefix only");
    }

    public void prefixThrowException(String args) throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(DateTimeParser.MESSAGE_INVALID_DATETIME_PREFIX_COMBI);
        dateTimeParser.parse(args);
    }

    @Test
    public void parse_invalidDateAfterPrefix_exceptionThrown() throws IllegalValueException {
        dateThrowException("valid prefix by/" + invalidDate);
        dateThrowException("valid prefix on/ with some other text before" + validEndDate);
    }

    public void dateThrowException(String args) throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        dateTimeParser.parse(args);
    }

    @Test
    public void parse_validDateargs() throws IllegalValueException {
        dateTimeParser.parse("this is an event from/ " + validStartDate + " to/ " + validEndDate);
        assertEquals(dateTimeParser.getStartDate().get(), validStartDate);
        assertEquals(dateTimeParser.getEndDate().get(), validEndDate);
        dateTimeParser.parse("by/ " + validEndDate + " some other additional description");
        assertEquals(dateTimeParser.getEndDate().get(), validEndDate);
    }

    @Test
    public void parse_extractUnparsedArgsCorrectly() throws IllegalValueException {
        String arg = "task description #tags from/ " + validStartDate + " more description to/ " + validEndDate;
        dateTimeParser.parse(arg);
        assertEquals(dateTimeParser.getUnparsedArgs(), "task description #tags more description");

        // check that subset or same word as date elsewhere in the string is not trimmed off
        arg = "buy fri chicken for friends on/ fri";
        dateTimeParser.parse(arg);
        assertEquals(dateTimeParser.getUnparsedArgs(), "buy fri chicken for friends");

        arg = "go to 7-11 to collect pay by/ 7-11";
        dateTimeParser.parse(arg);
        assertEquals(dateTimeParser.getUnparsedArgs(), "go to 7-11 to collect pay");
    }

}
