package seedu.task.logic.commands;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.google.api.services.calendar.model.Event;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.GoogleCalendar;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.logic.util.LogicHelper;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Remark;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 * Adds a task to KIT without prefixes for date and location.
 */
public class SmartAddCommand extends Command {

    public static final String COMMAND_WORD_1 = "smartadd";
    public static final String COMMAND_WORD_2 = "sa";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Flexibly adds a task without any prefix. "
            + "Parameters: DESCRIPTION [r/REMARK] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD_1
            + " Meet Jason for dinner tomorrow at 6pm in vivocity t/friends";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    // @@author A0140063X
    /**
     * Creates an SmartAddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws IOException if connection to google failed
     */
    public SmartAddCommand(String name, String remark, Set<String> tags)
            throws IllegalValueException, IOException {
        final Set<Tag> tagSet = new HashSet<>();

        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        try {
            this.toAdd = googleQuickAdd(name);
            toAdd.setTags(new UniqueTagList(tagSet));
            toAdd.setRemark(new Remark(remark));
        } catch (IOException ioe) {
            throw new IOException(GoogleCalendar.CONNECTION_FAIL_MESSAGE);
        }
    }

    // @@author A0140063X
    /**
     * This method takes in the description string, creates and returns the corresponding task.
     * Requires Internet as it utilizes Google's Quick Add API. Throw IOException if otherwise.
     *
     * @param description   Query to send to Google.
     * @return              Task created from description.
     * @throws IOException              If connection fails.
     * @throws IllegalValueException    If created event has no name.
     */
    private Task googleQuickAdd(String description) throws IOException, IllegalValueException {
        com.google.api.services.calendar.Calendar service = GoogleCalendar.getCalendarService();
        Event createdEvent = service.events().quickAdd(GoogleCalendar.CALENDAR_ID, description).execute();
        service.events().delete(GoogleCalendar.CALENDAR_ID, createdEvent.getId()).execute();

        return LogicHelper.createTaskFromEvent(createdEvent);
    }

    // @@author A0140063X-reused
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(0));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
