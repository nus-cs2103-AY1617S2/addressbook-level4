package org.teamstbf.yats.logic.commands.exceptions;

import org.teamstbf.yats.logic.commands.Command;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class CommandException extends Exception {
    public CommandException(String message) {
	super(message);
    }
}
