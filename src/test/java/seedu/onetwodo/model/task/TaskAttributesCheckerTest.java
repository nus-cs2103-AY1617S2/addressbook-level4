package seedu.onetwodo.model.task;

import java.time.LocalDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.tag.UniqueTagList;

//@@author A0141138N
public class TaskAttributesCheckerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Task[] testTasks = createSampleTasks();

    private Task[] createSampleTasks() {
        try {
            // CHECKSTYLE.OFF: LineLength
            return new Task[] {
                new Task(new Name("testA"), new StartDate("today"), new EndDate(""), new Recurring(""),
                            new Priority("l"), new Description(""), new UniqueTagList()),
                new Task(new Name("testB"), new StartDate("yesterday"), new EndDate(""), new Recurring(""),
                            new Priority("l"), new Description(""), new UniqueTagList()),
                new Task(new Name("testC"), new StartDate(""), new EndDate("yesterday"), new Recurring(""),
                            new Priority("l"), new Description(""), new UniqueTagList()),
                new Task(new Name("testD"), new StartDate("tomorrow 6pm"), new EndDate("today 1pm"),
                            new Recurring(""), new Priority("l"), new Description(""), new UniqueTagList()),
                new Task(new Name("testE"), new StartDate("today 1pm"), new EndDate("today 1pm"), new Recurring(""),
                            new Priority("l"), new Description(""), new UniqueTagList()),
                new Task(new Name("testG"), new StartDate(""), new EndDate("today 1pm"), new Recurring("monthly"),
                            new Priority("l"), new Description(""), new UniqueTagList()),
                new Task(new Name("testH"), new StartDate("today 1pm"), new EndDate("today 3pm"),
                            new Recurring("monthly"), new Priority("l"), new Description(""), new UniqueTagList()),
                new Task(new Name("testF"), new StartDate(""), new EndDate(""), new Recurring("weekly"),
                            new Priority("l"), new Description(""), new UniqueTagList()) };
            // CHECKSTYLE.ON: LineLength
        } catch (IllegalValueException e) {
            assert false;
            // not possible
            return null;
        }
    }

    @Test
    public void testCheckIsValidTodo() throws Exception {
        Task endMissing = testTasks[0];
        thrown.expect(IllegalValueException.class);
        TaskAttributesChecker.checkIsValidTodo(endMissing);
    }

    @Test
    public void testCheckIsValidStartDate() throws Exception {
        Task startBeforeCurrent = testTasks[1];
        LocalDateTime dateCreated;
        dateCreated = LocalDateTime.now();
        thrown.expect(IllegalValueException.class);
        TaskAttributesChecker.checkIsValidStartDate(startBeforeCurrent, dateCreated);
    }

    @Test
    public void testCheckIsValidEndDate() throws Exception {
        Task endBeforeCurrent = testTasks[2];
        LocalDateTime dateCreated;
        dateCreated = LocalDateTime.now();
        thrown.expect(IllegalValueException.class);
        TaskAttributesChecker.checkIsValidEndDate(endBeforeCurrent, dateCreated);
    }

    @Test
    public void testCheckIsValidEvent() throws Exception {

        Task endBeforeStart = testTasks[3];
        Task startEqualsEnd = testTasks[4];
        thrown.expect(IllegalValueException.class);
        TaskAttributesChecker.checkIsValidEvent(endBeforeStart);
        TaskAttributesChecker.checkIsValidEvent(startEqualsEnd);
    }

    @Test
    public void testCheckIsValidRecur() throws Exception {

        Task deadlineTask = testTasks[5];
        TaskAttributesChecker.checkIsValidRecur(deadlineTask);

        Task eventTask = testTasks[6];
        TaskAttributesChecker.checkIsValidRecur(eventTask);

        Task todoTask = testTasks[7];
        thrown.expect(IllegalValueException.class);
        TaskAttributesChecker.checkIsValidRecur(todoTask);
    }

    @Test
    public void testvalidateEditedAttributesNull() throws Exception {
        Task endMissing = testTasks[0];
        thrown.expect(CommandException.class);
        TaskAttributesChecker.validateEditedAttributes(endMissing);
    }
}
