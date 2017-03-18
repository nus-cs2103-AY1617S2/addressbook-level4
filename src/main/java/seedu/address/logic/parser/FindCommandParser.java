package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEINTERVAL_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEINTERVAL_START;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.IncorrectCommand;

//@@author A0162877N
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser extends Parser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        try {
            ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DEADLINE,
                    PREFIX_TIMEINTERVAL_START, PREFIX_TIMEINTERVAL_END);
            argsTokenizer.tokenize(args);
            if (args.trim().contains(PREFIX_TIMEINTERVAL_START.getPrefix())
                    && args.trim().contains(PREFIX_TIMEINTERVAL_END.getPrefix())) {
                return new FindCommand(argsTokenizer.getValue(PREFIX_TIMEINTERVAL_START).get(),
                        argsTokenizer.getValue(PREFIX_TIMEINTERVAL_END).get());
            } else if (args.trim().contains(PREFIX_DEADLINE.getPrefix())) {
                return new FindCommand(argsTokenizer.getValue(PREFIX_DEADLINE).get());
            }
        } catch (Exception e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}
