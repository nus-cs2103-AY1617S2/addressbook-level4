package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doit.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_END;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_START;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.FindCommand;
import seedu.doit.logic.commands.IncorrectCommand;
//@@author A0146809W
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements CommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns an FindCommand object for execution.
     */

    @Override
    public Command parse(String args) {

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_NAME, PREFIX_PRIORITY, PREFIX_START, PREFIX_END,
                PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        Set<String> nameKeyWordSet = new HashSet<>(
                argsTokenizer.getAllValuesAfterPrefixAndSpaces(PREFIX_NAME).orElse(Collections.emptyList()));
        Set<String> priorityKeyWordSet = new HashSet<>(
                argsTokenizer.getAllValuesAfterPrefixAndSpaces(PREFIX_PRIORITY).orElse(Collections.emptyList()));
        Set<String> startKeyWordSet = new HashSet<>(
                argsTokenizer.getAllValuesAfterPrefixAndSpaces(PREFIX_START).orElse(Collections.emptyList()));
        Set<String> deadlineKeyWordSet = new HashSet<>(
                argsTokenizer.getAllValuesAfterPrefixAndSpaces(PREFIX_END).orElse(Collections.emptyList()));
        Set<String> tagsKeyWordSet = new HashSet<>(
                argsTokenizer.getAllValuesAfterPrefixAndSpaces(PREFIX_TAG).orElse(Collections.emptyList()));
        Set<String> descKeyWordSet = new HashSet<>(
                argsTokenizer.getAllValuesAfterPrefixAndSpaces(PREFIX_DESCRIPTION).orElse(Collections.emptyList()));


        if (isAllKeyWordSetEmpty(nameKeyWordSet, priorityKeyWordSet, startKeyWordSet,
            deadlineKeyWordSet, tagsKeyWordSet, descKeyWordSet)) {
            return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        return new FindCommand(nameKeyWordSet, startKeyWordSet, deadlineKeyWordSet,
            priorityKeyWordSet, tagsKeyWordSet, descKeyWordSet);

    }

    private boolean isAllKeyWordSetEmpty (Set<String> nameKeyWordSet, Set<String> priorityKeyWordSet,
                                          Set<String> startKeyWordSet, Set<String> deadlineKeyWordSet,
                                          Set<String> tagsKeyWordSet, Set<String> descKeyWordSet) {

        return nameKeyWordSet.isEmpty() && priorityKeyWordSet.isEmpty() && startKeyWordSet.isEmpty()
            && deadlineKeyWordSet.isEmpty() && tagsKeyWordSet.isEmpty() && descKeyWordSet.isEmpty();
    }

}
