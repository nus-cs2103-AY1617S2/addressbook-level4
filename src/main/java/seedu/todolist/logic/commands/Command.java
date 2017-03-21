package seedu.todolist.logic.commands;

import seedu.todolist.commons.core.Config;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.UndoManager;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected UndoManager undoManager;
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
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, UndoManager undoManager, Config config) {
        this.model = model;
        this.undoManager = undoManager;
        this.config = config;
    }

    public abstract boolean isMutating();

    public abstract String getCommandText();
}
