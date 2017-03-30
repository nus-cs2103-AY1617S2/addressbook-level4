package seedu.tache.logic.commands;

import java.util.Stack;

import seedu.tache.commons.core.Config;
import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.Model;
import seedu.tache.storage.Storage;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected Storage storage;
    protected Config config;
    //@@author A0150120H
    protected static Stack<Undoable> undoHistory = new Stack<Undoable>();
    //@@author

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
    public void setData(Model model) {
        this.model = model;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    //@@author A0150120H
    /**
     * Clears the undo history
     */
    protected static void clearUndoHistory() {
        undoHistory = new Stack<Undoable>();
    }
    //@@author
}
