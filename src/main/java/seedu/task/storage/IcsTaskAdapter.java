//@@author A0144813J
package seedu.task.storage;

import java.net.SocketException;
import java.util.Calendar;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Priority;
import net.fortuna.ical4j.util.UidGenerator;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Instruction;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.Title;

public class IcsTaskAdapter {

    private static TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
    private static TimeZone timezone = registry.getTimeZone("Asia/Singapore");
    private static Calendar calendar = java.util.Calendar.getInstance(timezone);

    /**
     * Converts the given Task object into an ICS-friendly VEvent object.
     *
     * @param task The Task object to be converted.
     * @return the The VEvent object corresponds to the given task object.
     */
    public static VEvent taskToVEvent(ReadOnlyTask task) {

        Deadline deadline = task.getDeadline();
        calendar.setTime(deadline.nextDeadline());
        VEvent event = new VEvent(new Date(calendar.getTime()), task.getTitle().title);
        UidGenerator ug = null;
        try {
            ug = new UidGenerator("1");
        } catch (SocketException e1) {
        }
        event.getProperties().add(ug.generateUid());
        event.getProperties().add(new Priority(parsePriority(task)));
        event.getProperties().add(new Description(task.getInstruction().value));
        return event;
    }

    /**
     * Converts the given Component object to the model's Task object.
     *
     * @param component The component object to be converted.
     * @return the Task object corresponds to the given Component object.
     * @throws IllegalValueException thrown when the event data in component is not valid.
     */
    public static Task componentToTask(Component component) throws IllegalValueException {
        String titleString = parseSummary(component);
        String date = parseDate(component);
        String priorityString = parsePriority(component);
        String instructionString = parseDescription(component);

        Title title = new Title(titleString);
        Deadline deadline = new Deadline(date);
        seedu.task.model.task.Priority priority = new seedu.task.model.task.Priority(priorityString);
        Instruction instruction = new Instruction(instructionString);

        Task task = new Task(title, deadline, priority, instruction, new UniqueTagList());
        return task;
    }

    private static String parseSummary(Component component) {
        return component.getProperty("summary").getValue();
    }

    private static String parseDate(Component component) {
        String date = component.getProperty("dtstart").getValue();
        date = date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6);
        return date;
    }

    private static String parsePriority(Component component) {
        String priorityString = "3";
        if (component.getProperty("priority") != null) {
            priorityString = component.getProperty("priority").getValue();
        }
        return priorityString;
    }

    private static int parsePriority(ReadOnlyTask task) {
        String priorityString = task.getPriority().value;
        String priorityStringUnsigned = priorityString.replaceAll("-", "");

        return 6 - Integer.parseInt(priorityStringUnsigned);
    }

    private static String parseDescription(Component component) {
        String instructionString = "nothing yet";
        if (component.getProperty("description") != null) {
            instructionString = component.getProperty("description").getValue();
        }
        return instructionString;
    }

}
