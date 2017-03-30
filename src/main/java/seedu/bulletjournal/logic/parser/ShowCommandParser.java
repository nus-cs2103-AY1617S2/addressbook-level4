//@@author A0105748B
package seedu.bulletjournal.logic.parser;

import static seedu.bulletjournal.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bulletjournal.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.bulletjournal.logic.commands.Command;
import seedu.bulletjournal.logic.commands.IncorrectCommand;
import seedu.bulletjournal.logic.commands.ShowCommand;

/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns an ShowCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new ShowCommand(keywordSet);
    }

}
