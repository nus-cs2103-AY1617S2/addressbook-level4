package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SAVE;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.SaveToCommand;

/**
 * Parses input arguments and creates a new SaveToCommand object
 */
public class SaveToCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveToCommand
     * and returns an SaveToCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_SAVE);
        argsTokenizer.tokenize(args);
        Optional<List<String>> argsArray = argsTokenizer.getAllValues(PREFIX_SAVE);
        if (!argsArray.isPresent() || argsArray.get().size() != 1) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveToCommand.MESSAGE_USAGE));
        }

        String dirLocation = argsArray.get().get(0);
        return new SaveToCommand(dirLocation);
    }
}
