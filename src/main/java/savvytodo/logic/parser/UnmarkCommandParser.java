package savvytodo.logic.parser;

import static savvytodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import savvytodo.logic.commands.Command;
import savvytodo.logic.commands.IncorrectCommand;
import savvytodo.logic.commands.UnmarkCommand;

//@@author A0140016B
/**
 * Parses input arguments and creates a new UnmarkCommand object
 */
public class UnmarkCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkCommand
     * and returns an UnmarkCommand object for execution.
     */
    public Command parse(String args) {
        Optional<int[]> intArray = ParserUtil.parseMultipleInteger(args);
        if (!intArray.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }
        return new UnmarkCommand(intArray.get());
    }

}
//@@author A0140016B
