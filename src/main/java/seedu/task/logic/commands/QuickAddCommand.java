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
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Remark;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 * Quickly adds a task to KIT without prefixes.
 */
public class QuickAddCommand extends Command {

    public static final String COMMAND_WORD_1 = "qa";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Quickly adds a task without any prefix. "
            + "Parameters: DESCRIPTION [r/REMARK] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD_1
            + " Meet Jason for dinner tomorrow at 6pm in vivocity t/friends";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    // @@author A0140063X
    /**
     * Creates an QuickAddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws IOException if connection to google failed
     */
    public QuickAddCommand(String name, String remark, Set<String> tags)
            throws IllegalValueException, IOException {
        final Set<Tag> tagSet = new HashSet<>();

        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        try {
            this.toAdd = GoogleQuickAdd(name, remark, tagSet);
        } catch (IOException ioe) {
            throw new IOException(GoogleCalendar.CONNECTION_FAIL_MESSAGE);
        }
    }

    // @@author A0140063X
    private Task GoogleQuickAdd(String name, String remark, Set<Tag> tagSet) throws IOException, IllegalValueException {
        com.google.api.services.calendar.Calendar service = GoogleCalendar.getCalendarService();
        Event createdEvent = service.events().quickAdd(GoogleCalendar.CALENDAR_ID, name).execute();
        service.events().delete(GoogleCalendar.CALENDAR_ID, createdEvent.getId()).execute();

        Task toAdd = createTaskFromEvent(createdEvent);
        toAdd.setTags(new UniqueTagList(tagSet));
        toAdd.setRemark(new Remark(remark));

        return toAdd;
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
