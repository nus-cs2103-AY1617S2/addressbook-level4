package seedu.task.logic.commands;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.google.api.services.calendar.model.Event;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Date;
import seedu.task.model.task.Location;
import seedu.task.model.task.Name;
import seedu.task.model.task.Remark;
import seedu.task.model.task.Task;
import seedu.task.storage.Storage;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected Storage storage;

    /**
     * Constructs a feedback message to summarize an operation that displayed a listing of tasks.
     *
     * @param displaySize
     *            used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    public static String getMessageForDoneTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_DONE_LISTED_OVERVIEW, displaySize);
    }

    public static String getMessageForUnDoneTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_UNDONE_LISTED_OVERVIEW, displaySize);
    }

    public static String getMessageForFloatingTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_FLOAT_LISTED_OVERVIEW, displaySize);
    }

    public static String getMessageForTagTaskListShownSummary(int displaySize, String tag) {
        return String.format(Messages.MESSAGE_TASKS_TAG_LISTED_OVERVIEW, displaySize, tag);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException
     *             If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    // @@author A0140063X
    /**
     * Provides any needed dependencies to the command. Commands making use of any of these should override this method
     * to gain access to the dependencies.
     */
    public void setData(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
    }

    // @@author A0140063X
    protected ReadOnlyTaskManager readTaskManager(String filePath) throws IOException, IllegalValueException {
        try {
            Optional<ReadOnlyTaskManager> taskManagerOptional = storage.readTaskManager(filePath);

            if (!taskManagerOptional.isPresent()) {
                throw new IOException("File not found.");
            }

            return taskManagerOptional.get();
        } catch (DataConversionException dce) {
            throw new IOException("Data conversion error.");
        } catch (IOException ioe) {
            throw new IOException("File not found.");
        }
    }

    // @@author A0140063X
    protected static Task createTaskFromEvent(Event event) throws IllegalValueException {
        if (event.getSummary() == null) {
            throw new IllegalValueException("Name must not be empty.");
        }
        Name name = new Name(event.getSummary());
        Date startDate = new Date(event.getStart());
        Date endDate = new Date(event.getEnd());
        System.out.println(startDate.toString()+"oh shit");
        Remark remark = new Remark(event.getDescription());
        Location location = new Location(event.getLocation());
        final Set<Tag> tagSet = new HashSet<>(); // No tags

        return new Task(name, startDate, endDate, remark, location, new UniqueTagList(tagSet), false, event.getId());
    }
}
