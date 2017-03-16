package savvytodo.logic.parser;

import static savvytodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;

import savvytodo.logic.commands.Command;
import savvytodo.logic.commands.IncorrectCommand;
import savvytodo.logic.commands.LoadCommand;

/**
 * @author A0147827U
 * Parses input arguments and creates a new LoadCommand object
 */
public class LoadCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the LoadCommand
     * and returns an LoadCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer();
        argsTokenizer.tokenize(args);
        try {
            return new LoadCommand(argsTokenizer.getPreamble().get());
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        }
    }

}
