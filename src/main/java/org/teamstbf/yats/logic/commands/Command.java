package org.teamstbf.yats.logic.commands;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be
 * executed.
 */
public abstract class Command {
    protected Model model;

    /**
     * Constructs a feedback message to summarise an operation that displayed a
     * listing of persons.
     *
     * @param displaySize
     *            used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(int displaySize) {
	return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, displaySize);
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

}
