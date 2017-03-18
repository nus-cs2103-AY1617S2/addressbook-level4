package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ViewCommand;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     */
    public Command parse(String args) {

        try {
            System.out.println(args);
            return new ViewCommand(args.split("\\s+"));
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
    }

}
