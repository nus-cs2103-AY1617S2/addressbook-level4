package seedu.whatsleft.model.util;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;
import seedu.whatsleft.model.WhatsLeft;
import seedu.whatsleft.model.activity.ByDate;
import seedu.whatsleft.model.activity.ByTime;
import seedu.whatsleft.model.activity.Description;
import seedu.whatsleft.model.activity.EndDate;
import seedu.whatsleft.model.activity.EndTime;
import seedu.whatsleft.model.activity.Event;
import seedu.whatsleft.model.activity.Location;
import seedu.whatsleft.model.activity.Priority;
import seedu.whatsleft.model.activity.StartDate;
import seedu.whatsleft.model.activity.StartTime;
import seedu.whatsleft.model.activity.Task;
import seedu.whatsleft.model.activity.UniqueEventList.DuplicateEventException;
import seedu.whatsleft.model.activity.UniqueTaskList.DuplicateTaskException;
import seedu.whatsleft.model.tag.UniqueTagList;

//@@author A0148038A
public class SampleDataUtil {
    public static Event[] getSampleEvents() {
        try {
            return new Event[] {
                new Event(new Description("Welcome! Events are shown here!"), new StartTime("0001"),
                        new StartDate("today"),  new EndTime("2359"), new EndDate("today"),
                    new Location(null), new UniqueTagList()),

            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static Task[] getSampleTasks() {
        LocalDate testdate = null;
        LocalTime testtime = null;
        try {
            return new Task[] {
                new Task(new Description("You will find Tasks here!"), new Priority("high"),
                        new ByTime("2359"), new ByDate("today"),
                    new Location("null"), new UniqueTagList(), false),

            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyWhatsLeft getSampleWhatsLeft() {
        try {
            WhatsLeft sampleWhatsLeft = new WhatsLeft();
            for (Event sampleEvent : getSampleEvents()) {
                sampleWhatsLeft.addEvent(sampleEvent);
            }
            for (Task sampleTask : getSampleTasks()) {
                sampleWhatsLeft.addTask(sampleTask);
            }
            return sampleWhatsLeft;
        } catch (DuplicateEventException e) {
            throw new AssertionError("sample data cannot contain duplicate events", e);
        } catch (DuplicateTaskException t) {
            throw new AssertionError("sample data cannot contain duplicate tasks", t);
        }
    }
}
