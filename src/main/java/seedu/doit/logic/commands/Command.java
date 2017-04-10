package seedu.doit.logic.commands;

import seedu.doit.commons.core.Messages;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.Model;
import seedu.doit.storage.Storage;

/**
 * Represents a command with hidden internal logic and the ability to be
 * executed.
 */
public abstract class Command {
    protected Model model;
    protected Storage storage;
    /**
     * Return the name of this command.
     */
    public static String getName() {
        return null;
    }

    /**
     * Return the parameter of this command.
     */
    public static String getParameter() {
        return null;
    }

    /**
     * Return the description of this command.
     */
    public static String getResult() {
        return null;
    }

    /**
     * Return the example of this command.
     */
    public static String getExample() {
        return null;
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a
     * listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Constructs a feedback message to summarise an operation that sorted a
     * listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSortedSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_SORTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command. Commands making use of
     * any of these should override this method to gain access to the
     * dependencies.
     *
     * @param model
     * @param storage
     */
    public void setData(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
    }
}
