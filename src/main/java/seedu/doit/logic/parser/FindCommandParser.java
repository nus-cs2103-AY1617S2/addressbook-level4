package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doit.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_END;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_START;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.FindCommand;
import seedu.doit.logic.commands.IncorrectCommand;

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
            new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_START, PREFIX_END, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);

        Set<String> priorityKeyWordSet = new HashSet<>(argsTokenizer.getAllValues(PREFIX_PRIORITY)
            .orElse(Collections.emptyList()));
        Set<String> startKeyWordSet = new HashSet<>(argsTokenizer.getAllValues(PREFIX_START)
            .orElse(Collections.emptyList()));
        Set<String> deadlineKeyWordSet = new HashSet<>(argsTokenizer.getAllValues(PREFIX_END)
            .orElse(Collections.emptyList()));
        Set<String> tagsKeyWordSet = new HashSet<>(argsTokenizer.getAllValues(PREFIX_TAG)
            .orElse(Collections.emptyList()));
        Set<String> descKeyWordSet = new HashSet<>(argsTokenizer.getAllValues(PREFIX_DESCRIPTION)
            .orElse(Collections.emptyList()));


        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet, startKeyWordSet, deadlineKeyWordSet,
            priorityKeyWordSet, tagsKeyWordSet, descKeyWordSet);

    }

}
