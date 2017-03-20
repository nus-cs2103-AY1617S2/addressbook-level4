package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_INFORMATION;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;

import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.FindCommand;
import seedu.taskboss.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    private static final String EMPTY_STRING = "";

    //@@author A0147990R
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_NAME, PREFIX_START_DATE,
                        PREFIX_END_DATE, PREFIX_INFORMATION);
        argsTokenizer.tokenize(args);
        String nameValue = checkEmpty(argsTokenizer.getValue(PREFIX_NAME));
        String startDatetimeValue = checkEmpty(argsTokenizer.getValue(PREFIX_START_DATE));
        String endDatetimeValue = checkEmpty(argsTokenizer.getValue(PREFIX_END_DATE));
        String informationValue = checkEmpty(argsTokenizer.getValue(PREFIX_INFORMATION));

        if (notValid(nameValue, startDatetimeValue, endDatetimeValue, informationValue)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        } else {
            String keywords = getNoneNullValue(nameValue, startDatetimeValue,
                    endDatetimeValue, informationValue);
            String prefix = getPrefixString(nameValue, startDatetimeValue,
                    endDatetimeValue, informationValue);

            final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(keywords.trim());
            if (!matcher.matches()) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            // keywords delimited by whitespace
            return new FindCommand(prefix, keywords);
        }

    }

    //@@author A0147990R
    private String checkEmpty(Optional<String> test) {
        try {
            return test.get();
        } catch (NoSuchElementException nsee) {
            return EMPTY_STRING;
        }
    }

    //@@author A0147990R
    private boolean notValid(String test1, String test2, String test3, String test4) {
        String sum = test1 + test2 + test3 + test4;
        return (!sum.equals(test1) && !sum.equals(test2) && !sum.equals(test3) && !sum.equals(test4));
    }

    //@@author A0147990R
    private String getNoneNullValue(String test1, String test2, String test3, String test4) {
        return test1 + test2 + test3 + test4;
    }

    //@@author A0147990R
    private String getPrefixString(String test1, String test2, String test3, String test4) {
        String sum = test1 + test2 + test3 + test4;
        if (sum.equals(test1)) {
            return PREFIX_NAME.toString();
        } else if (sum.equals(test2)) {
            return PREFIX_START_DATE.toString();
        } else if (sum.equals(test3)) {
            return PREFIX_END_DATE.toString();
        } else {
            return PREFIX_INFORMATION.toString();
        }
    }

}
