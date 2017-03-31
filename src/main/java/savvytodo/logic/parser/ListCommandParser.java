package savvytodo.logic.parser;

import static savvytodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static savvytodo.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static savvytodo.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.NoSuchElementException;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.logic.commands.Command;
import savvytodo.logic.commands.IncorrectCommand;
import savvytodo.logic.commands.ListCommand;

//@@A0124863A
/**
 * @author A0124863A
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_CATEGORY);
        argsTokenizer.tokenize(args);

        try {
            return new ListCommand(argsTokenizer.getValue(PREFIX_PRIORITY),
                    argsTokenizer.getValue(PREFIX_CATEGORY));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }

}
