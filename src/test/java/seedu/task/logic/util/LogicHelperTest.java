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
    public void createTaskFromEvent_validEvent_success() throws IllegalValueException {
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


    }

    //event with no summary should throw exception
    @Test
    public void createTaskFromEvent_noSummary_exceptionThrown() throws IllegalValueException {
        testEvent.setSummary(null);
        exception.expect(IllegalValueException.class);
        LogicHelper.createTaskFromEvent(testEvent);

    }

    @Test
    public void createEventFromTask_validTask_success() throws IllegalValueException {
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

    /**
     * Check if task created from testEvent matches expectedTask.
     *
     * @param expectedTask      Task that is expected.
     * @param testEvent         Event that is being tested.
     * @throws IllegalValueException    If event does not have summary.
     */
    private void assertCreateTaskFromEvent(TestTask expectedTask, Event testEvent) throws IllegalValueException {
        Task actualTask = LogicHelper.createTaskFromEvent(testEvent);
        assertTrue(expectedTask.isSameStateAs(actualTask));
    }

    /**
     * Check if event created from testTask matches expectedEvent.
     *
     * @param testTask          Task that is being tested.
     * @param expectedEvent     Event that is expected.
     */
    private void assertCreateEventFromTask(TestTask testTask, Event expectedEvent) {
        Event actualEvent = LogicHelper.createEventFromTask(testTask);
        assertTrue(expectedEvent.equals(actualEvent));
    }

    /**
     * Helper method to create event from given details.
     *
     * @param summary       Summary of event.
     * @param start         Start DateTime of event.
     * @param end           End DateTime of event.
     * @param description   Description of event.
     * @param location      Location of event.
     * @return              Event created from given details.
     */
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
