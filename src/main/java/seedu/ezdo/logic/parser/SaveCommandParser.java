//@@author A0139248X
package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.commands.SaveCommand;

public class SaveCommandParser implements CommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveCommand
     * and returns a SaveCommand object for execution.
     * Returns an IncorrectCommand if there is an IllegalValueException or NoSuchElementException
     */
    @Override
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer();
        argsTokenizer.tokenize(args);
        try {
            return new SaveCommand(argsTokenizer.getPreamble().get());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }
    }

}
