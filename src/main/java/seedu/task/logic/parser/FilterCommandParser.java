package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.FilterCommand;

//@@author A0163845X
public class FilterCommandParser {


	public Command parse(String arguments) {
		arguments = arguments.toLowerCase();
		arguments = arguments.trim();
		return new FilterCommand(arguments);
	}

}
