package seedu.task.logic.util;

import java.util.HashSet;
import java.util.Set;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Date;
import seedu.task.model.task.Location;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Remark;
import seedu.task.model.task.Task;

// @@author A0140063X
public class LogicHelper {

    /**
     * This method returns a task from the given event. Name must not be empty, else IllegalValueException is thrown.
     *
     * @param event     Event to convert.
     * @return          Task that was converted.
     * @throws IllegalValueException    If event summary is empty.
     */
    public static Task createTaskFromEvent(Event event) throws IllegalValueException {
        assert event != null;

        if (event.getSummary() == null) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }

        Name name = new Name(event.getSummary());
        Date startDate = new Date(event.getStart());
        Date endDate = new Date(event.getEnd());
        Remark remark = new Remark(event.getDescription());
        Location location = new Location(event.getLocation());
        final Set<Tag> tagSet = new HashSet<>();
        // No tags for event

        return new Task(name, startDate, endDate, remark, location, new UniqueTagList(tagSet), false, event.getId());
    }

    /**
     * This method returns an event for the given task. Task must have start and end date to be an event.
     *
     * @param task      Task to convert.
     * @return          Event that was converted.
     */
    public static Event createEventFromTask(ReadOnlyTask task) {
        assert task != null;
        assert task.getStartDate() != null;
        assert task.getEndDate() != null;

        Event event = new Event()
                .setSummary(task.getName().fullName)
                .setLocation(task.getLocation().value)
                .setDescription(task.getRemark().value);

        DateTime startDateTime = new DateTime(task.getStartDate().getDateValue());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime(task.getEndDate().getDateValue());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);
        event.setEnd(end);

        event.setId(task.getEventId());

        return event;
    }
}
