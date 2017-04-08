package seedu.address.logic.parser;

// import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
// import static seedu.address.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;

// import java.util.regex.Matcher;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DEADLINE);
        argsTokenizer.tokenize(args);

        String name = argsTokenizer.getPreamble().orElse("");
        String deadline = argsTokenizer.getValue(PREFIX_DEADLINE).orElse(null);

        // add format here
        try {
            return new FindCommand(name, deadline);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }

}
