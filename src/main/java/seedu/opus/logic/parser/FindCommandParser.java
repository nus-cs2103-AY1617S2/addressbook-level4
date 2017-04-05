package seedu.opus.logic.parser;

import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.opus.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.opus.logic.commands.Command;
import seedu.opus.logic.commands.FindCommand;
import seedu.opus.logic.commands.IncorrectCommand;
import seedu.opus.model.qualifier.EndTimeQualifier;
import seedu.opus.model.qualifier.KeywordQualifier;
import seedu.opus.model.qualifier.PriorityQualifier;
import seedu.opus.model.qualifier.Qualifier;
import seedu.opus.model.qualifier.StartTimeQualifier;
import seedu.opus.model.qualifier.StatusQualifier;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    //@@author A0126345J
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {

        List<Qualifier> qualifiers = new ArrayList<Qualifier>();

        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_STATUS,
                        PREFIX_STARTTIME, PREFIX_ENDTIME);
        argsTokenizer.tokenize(args);
        String inputKeyword = argsTokenizer.getPreamble().orElse("");

        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(inputKeyword.trim());
        if (matcher.matches()) {
            // keywords delimited by whitespace
            final String[] keywords = matcher.group("keywords").split("\\s+");
            final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
            qualifiers.add(new KeywordQualifier(keywordSet));
        }

        // Bulid extra qualifier list
        if (argsTokenizer.getValue(PREFIX_PRIORITY).isPresent()) {
            qualifiers.add(new PriorityQualifier(argsTokenizer.getValue(PREFIX_PRIORITY).orElse("")));
        }

        if (argsTokenizer.getValue(PREFIX_STATUS).isPresent()) {
            qualifiers.add(new StatusQualifier(argsTokenizer.getValue(PREFIX_STATUS).orElse("")));
        }

        if (argsTokenizer.getValue(PREFIX_STARTTIME).isPresent()) {
            qualifiers.add(new StartTimeQualifier(argsTokenizer.getValue(PREFIX_STARTTIME).orElse("")));
        }

        if (argsTokenizer.getValue(PREFIX_ENDTIME).isPresent()) {
            qualifiers.add(new EndTimeQualifier(argsTokenizer.getValue(PREFIX_ENDTIME).orElse("")));
        }

        // has no qualifier
        if (qualifiers.size() < 1) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(qualifiers);
    }
    //@@author

}
