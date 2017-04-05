package seedu.geekeep.logic.parser;

import static seedu.geekeep.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.geekeep.logic.parser.CliSyntax.PREFIX_AFTER_DATETIME;
import static seedu.geekeep.logic.parser.CliSyntax.PREFIX_BEFORE_DATETIME;
import static seedu.geekeep.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.logic.commands.Command;
import seedu.geekeep.logic.commands.FindCommand;
import seedu.geekeep.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                    new ArgumentTokenizer(PREFIX_BEFORE_DATETIME, PREFIX_AFTER_DATETIME, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        Optional<String> keywordInTitle = argsTokenizer.getPreamble();
        Optional<String> earliestTime = argsTokenizer.getValue(PREFIX_AFTER_DATETIME);
        Optional<String> latestTime = argsTokenizer.getValue(PREFIX_BEFORE_DATETIME);
        Set<String> tags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));

        boolean isAnyParameterExisting = keywordInTitle.isPresent() || earliestTime.isPresent()
                || latestTime.isPresent() || !tags.isEmpty();
        if (!isAnyParameterExisting) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = null;
        Set<String> keywordSet = new HashSet<>();
        if (keywordInTitle.isPresent()) {
            keywords = keywordInTitle.get().split("\\s+"); // keywords delimited by whitespace
            keywordSet = new HashSet<>(Arrays.asList(keywords));
        }

        try {
            return new FindCommand(keywordSet, earliestTime, latestTime, tags);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
