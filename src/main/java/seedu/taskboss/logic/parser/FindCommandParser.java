package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.FindCommand;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.parser.ArgumentTokenizer.Prefix;

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
                new ArgumentTokenizer(PREFIX_NAME, PREFIX_START_DATE, PREFIX_END_DATE);
        argsTokenizer.tokenize(args);
        String nameValue = checkEmpty(argsTokenizer.getValue(PREFIX_NAME));
        String startDatetimeValue = checkEmpty(argsTokenizer.getValue(PREFIX_START_DATE));
        String endDatetimeValue = checkEmpty(argsTokenizer.getValue(PREFIX_END_DATE));
        
        if (notValid(nameValue, startDatetimeValue, endDatetimeValue)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        } else {
            String value = getValue(nameValue, startDatetimeValue, endDatetimeValue);
            String prefix = getPrefixString(nameValue, startDatetimeValue, endDatetimeValue);
            
            final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(value.trim());
            if (!matcher.matches()) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            // keywords delimited by whitespace
            final String[] keywords = matcher.group("keywords").split("\\s+");
            final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
            return new FindCommand(prefix, keywordSet); 
        }
            
    }
    
    private String checkEmpty(Optional<String> test) {
        try {
            return test.get();
        } catch (NoSuchElementException nsee) {
            return "";
        }
    }
    
    private boolean notValid(String test1, String test2, String test3) {
        String sum = test1 + test2 + test3;
        System.out.println("sum " + sum);
        return (!sum.equals(test1) && !sum.equals(test2) && !sum.equals(test3));
    }
    
    private String getValue(String test1, String test2, String test3) {
        return test1 + test2 + test3;
    }
    
    private String getPrefixString(String test1, String test2, String test3) {
        String sum = test1 + test2 + test3;
        if (sum.equals(test1)) {
            return PREFIX_NAME.toString();
        } else if (sum.equals(test2)) {
            return PREFIX_START_DATE.toString();
        } else {
            return PREFIX_END_DATE.toString();
        }
    }

}
