//@@author A0141138N
package seedu.onetwodo.model.task;

import java.time.LocalDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.model.tag.UniqueTagList;

public class TaskAttributesCheckerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCheckIsValidTodo() throws Exception {
        Task testA = new Task(new Name("testA"), new StartDate("today"), new EndDate(""), new Recurring(""),
                new Priority("l"), new Description(""), new UniqueTagList());
        thrown.expect(IllegalValueException.class);
        TaskAttributesChecker.checkIsValidTodo(testA);
    }

    @Test
    public void testCheckIsValidStartDate() throws Exception {
        Task testB = new Task(new Name("testB"), new StartDate("yesterday"), new EndDate(""), new Recurring(""),
                new Priority("l"), new Description(""), new UniqueTagList());
        LocalDateTime dateCreated;
        dateCreated = LocalDateTime.now();
        thrown.expect(IllegalValueException.class);
        TaskAttributesChecker.checkIsValidStartDate(testB, dateCreated);
    }

    @Test
    public void testCheckIsValidEndDate() throws Exception {
        Task testC = new Task(new Name("testC"), new StartDate(""), new EndDate("yesterday"), new Recurring(""),
                new Priority("l"), new Description(""), new UniqueTagList());
        LocalDateTime dateCreated;
        dateCreated = LocalDateTime.now();
        thrown.expect(IllegalValueException.class);
        TaskAttributesChecker.checkIsValidEndDate(testC, dateCreated);
    }

    @Test
    public void testCheckIsValidEvent() throws Exception {

        Task testD = new Task(new Name("testD"), new StartDate("tomorrow 6pm"), new EndDate("today 1pm"),
                new Recurring(""), new Priority("l"), new Description(""), new UniqueTagList());
        Task testE = new Task(new Name("testE"), new StartDate("today 1pm"), new EndDate("today 1pm"),
                new Recurring(""), new Priority("l"), new Description(""), new UniqueTagList());
        thrown.expect(IllegalValueException.class);
        TaskAttributesChecker.checkIsValidEvent(testD);
        TaskAttributesChecker.checkIsValidEvent(testE);
    }

}
