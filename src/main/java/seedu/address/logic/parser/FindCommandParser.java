package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

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
    			new ArgumentTokenizer(PREFIX_NAME);
    	argsTokenizer.tokenize(args);
    	try {
            Prefix prefix = argsTokenizer.getPrefixs().iterator().next();
            String pre = prefix.getPrefix();
            String value = argsTokenizer.getValue(PREFIX_NAME).get();

	        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(value.trim());
            if (!matcher.matches()) {
                return new IncorrectCommand(
	                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
	        // keywords delimited by whitespace
            final String[] keywords = matcher.group("keywords").split("\\s+");
            final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
            return new FindCommand(pre, keywordSet);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
