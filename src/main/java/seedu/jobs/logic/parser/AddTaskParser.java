package seedu.jobs.logic.parser;


import java.util.NoSuchElementException;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.logic.commands.AddTaskCommand;
import seedu.jobs.logic.commands.Command;

public class AddTaskParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
    	try {
			return new AddTaskCommand(args);
		} catch (IllegalValueException e) {
			e.printStackTrace();
		}
		return null;
    }
}
