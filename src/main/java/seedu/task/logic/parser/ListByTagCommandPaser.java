package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ListByTagCommand;

public class ListByTagCommandPaser {

	public Command parse(String arguments) {
		return new ListByTagCommand(arguments);
	}

}
