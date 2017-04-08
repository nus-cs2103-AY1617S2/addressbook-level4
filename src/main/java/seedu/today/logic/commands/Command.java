package seedu.today.logic.commands;

import seedu.today.commons.core.Config;
import seedu.today.commons.core.Messages;
import seedu.today.logic.commands.exceptions.CommandException;
import seedu.today.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be
 * executed.
 */
public abstract class Command {
    protected Model model;
    protected Config config;

    /**
     * Constructs a feedback message to summarise an operation that displayed a
     * listing of tasks.
     *
     * @param displaySize
     *            used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException
     *             If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command. Commands making use of
     * any of these should override this method to gain access to the
     * dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
