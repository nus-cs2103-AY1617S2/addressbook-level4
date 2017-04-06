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

    public static Task createTaskFromEvent(Event event) throws IllegalValueException {
        assert event != null;

        if (event.getSummary() == null) {
            throw new IllegalValueException("Name must not be empty.");
        }
        Name name = new Name(event.getSummary());
        Date startDate = new Date(event.getStart());
        Date endDate = new Date(event.getEnd());
        Remark remark = new Remark(event.getDescription());
        Location location = new Location(event.getLocation());
        final Set<Tag> tagSet = new HashSet<>(); // No tags

        return new Task(name, startDate, endDate, remark, location, new UniqueTagList(tagSet), false, event.getId());
    }

    public static Event createEventFromTask(ReadOnlyTask taskToPost) {
        assert taskToPost != null;
        assert taskToPost.getStartDate() != null;
        assert taskToPost.getEndDate() != null;

        Event event = new Event()
                .setSummary(taskToPost.getName().fullName)
                .setLocation(taskToPost.getLocation().value)
                .setDescription(taskToPost.getRemark().value);

        DateTime startDateTime = new DateTime(taskToPost.getStartDate().getDateValue());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime(taskToPost.getEndDate().getDateValue());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);
        event.setEnd(end);

        event.setId(taskToPost.getEventId());

        return event;
    }
}
