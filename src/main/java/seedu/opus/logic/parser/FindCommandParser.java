package seedu.opus.logic.parser;

import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.opus.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.opus.logic.commands.Command;
import seedu.opus.logic.commands.FindCommand;
import seedu.opus.logic.commands.IncorrectCommand;
import seedu.opus.model.qualifier.NameQualifier;
import seedu.opus.model.qualifier.NoteQualifier;
import seedu.opus.model.qualifier.Qualifier;
import seedu.opus.model.qualifier.TagQualifier;

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
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_STATUS, PREFIX_STARTTIME, PREFIX_ENDTIME);
        argsTokenizer.tokenize(args);
        String inputKeyword = argsTokenizer.getPreamble().orElse("");

        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(inputKeyword.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        List<Qualifier> qualifiers = Arrays.asList(
                new NameQualifier(keywordSet), new NoteQualifier(keywordSet), new TagQualifier(keywordSet));
        return new FindCommand(qualifiers);
    }

}
