package seedu.task.logic.commands;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.task.ReadOnlyTask;
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
     * @param model
     * @param storage
     */
    public void setData(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
    }

    // @@author A0140063X
    /**
     * This method gets storage to read taskmanager data from the given filePath and returns the data back to caller.
     * @param filePath
     * @return
     * @throws IOException
     * @throws IllegalValueException
     */
    protected ReadOnlyTaskManager readTaskManager(String filePath) throws IOException, IllegalValueException {
        assert storage != null;
        try {
            Optional<ReadOnlyTaskManager> taskManagerOptional = storage.readTaskManager(filePath);

            if (!taskManagerOptional.isPresent()) {
                throw new IOException("File not found.");
            }

            return taskManagerOptional.get();
        } catch (DataConversionException dce) {
            throw new IOException("Data conversion error.");
        } catch (IOException ioe) {
            throw new IOException("Unable to read from file.");
        }
    }

    // @@author A0140063X
    /**
     * This method returns the task for the given index. CommandException is thrown if index is invalid.
     * Otherwise the correct task will be returned as a ReadOnlyTask.
     * @param index
     * @return
     * @throws CommandException
     */
    protected ReadOnlyTask getTaskFromIndex(int index) throws CommandException {
        assert model != null;
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (index >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return lastShownList.get(index);
    }
}
