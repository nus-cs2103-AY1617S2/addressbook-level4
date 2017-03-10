package t15b1.taskcrusher.logic.parser;

import static t15b1.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static t15b1.taskcrusher.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
import t15b1.taskcrusher.logic.commands.AddCommand;
import t15b1.taskcrusher.logic.commands.Command;
import t15b1.taskcrusher.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DEADLINE_TIME, PREFIX_TAG, PREFIX_PRIORITY, PREFIX_DESCRIPTION);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_DESCRIPTION).get(),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
