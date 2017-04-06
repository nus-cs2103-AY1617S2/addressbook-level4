package seedu.task.logic.util;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.Location;
import seedu.task.model.task.Remark;
import seedu.task.model.task.Task;
import seedu.task.testutil.TaskBuilder;
import seedu.task.testutil.TestTask;

//@@author A0140063X
public class LogicHelperTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private String name = "Update boss on project progress.";
    private String start = "4th April 3pm";
    private String end = "4th April 5pm";
    private String remark = "Discuss about lack of funds";
    private String location = "Meeting room";
    private Event testEvent = createEvent(name, start, end, remark, location);

    @Test
    public void createTaskFromEvent() throws IllegalValueException {
        //event with full details
        TestTask testTask = new TaskBuilder().withName(name)
                .withStartDate(start).withEndDate(end)
                .withRemark(remark).withLocation(location).build();

        assertCreateTaskFromEvent(testTask, testEvent);

        //event with no description and location
        testEvent.setDescription("");
        testEvent.setLocation("");
        testTask.setRemark(new Remark(""));
        testTask.setLocation(new Location(""));
        assertCreateTaskFromEvent(testTask, testEvent);

        //event with no summary should throw exception
        testEvent.setSummary(null);
        exception.expect(IllegalValueException.class);
        LogicHelper.createTaskFromEvent(testEvent);

    }

    @Test
    public void createEventFromTask() throws IllegalValueException {
        //task with full details
        TestTask testTask = new TaskBuilder().withName(name)
                .withStartDate(start).withEndDate(end)
                .withRemark(remark).withLocation(location).build();
        assertCreateEventFromTask(testTask, testEvent);

        //task with no description and location
        testEvent.setDescription("");
        testEvent.setLocation("");
        testTask.setLocation(new Location(""));
        testTask.setRemark(new Remark(""));
        assertCreateEventFromTask(testTask, testEvent);
    }

    private void assertCreateTaskFromEvent(TestTask expectedTask, Event testEvent) throws IllegalValueException {
        Task actualTask = LogicHelper.createTaskFromEvent(testEvent);
        assertTrue(expectedTask.isSameStateAs(actualTask));
    }

    private void assertCreateEventFromTask(TestTask testTask, Event expectedEvent) throws IllegalValueException {
        Event actualEvent = LogicHelper.createEventFromTask(testTask);
        assertTrue(expectedEvent.equals(actualEvent));
    }

    private Event createEvent(String summary, String start, String end, String description, String location) {
        Event testEvent = new Event();
        testEvent.setSummary(summary);

        PrettyTimeParser pretty = new PrettyTimeParser();

        EventDateTime startDateTime = new EventDateTime();
        startDateTime.setDateTime(new DateTime(pretty.parse(start).get(0)));
        testEvent.setStart(startDateTime);

        EventDateTime endDateTime = new EventDateTime();
        endDateTime.setDateTime(new DateTime(pretty.parse(end).get(0)));
        testEvent.setEnd(endDateTime);

        testEvent.setDescription(description);
        testEvent.setLocation(location);
        return testEvent;
    }

}
