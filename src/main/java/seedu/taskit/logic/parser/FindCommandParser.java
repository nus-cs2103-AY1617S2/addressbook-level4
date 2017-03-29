package seedu.taskit.logic.parser;

import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskit.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.taskit.logic.commands.Command;
import seedu.taskit.logic.commands.FindCommand;
import seedu.taskit.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        
        
        //@@author A0097141H
        // keywords delimited by whitespace
        // if keywords are wrapped with double inverted commas, consider whole string as 1 keyword
        String[] keywords = {"keyword"};
        final Set<String> keywordSet;
        if(matcher.group("keywords").charAt(0) == '"' && matcher.group("keywords").charAt(matcher.group("keywords").length()-1) == '"')
        {
        	keywords[0] = matcher.group("keywords").replaceAll("\"", "");
        }
        else{
        	keywords = matcher.group("keywords").split("\\s+");
        	
        }
        keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}
