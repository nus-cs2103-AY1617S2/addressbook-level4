package typetask.logic.commands;

import java.io.IOException;

import typetask.commons.core.Config;
import typetask.commons.core.Messages;
import typetask.commons.exceptions.DataConversionException;
import typetask.logic.commands.exceptions.CommandException;
import typetask.model.Model;
import typetask.storage.Storage;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected Storage storage;
    protected Config config;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     * @throws IOException
     * @throws DataConversionException
     */
    public abstract CommandResult execute() throws CommandException, IOException, DataConversionException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, Storage storage, Config config) {
        this.model = model;
        this.storage = storage;
        this.config = config;
    }

}
