package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_INFORMATION;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.NoSuchElementException;
import java.util.regex.Matcher;

import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.FindCommand;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    private static final String EMPTY_STRING = "";
    private static final String prefixName = PREFIX_NAME.getPrefix();
    private static final String prefixInformation = PREFIX_INFORMATION.getPrefix();
    private static final String prefixStartDate = PREFIX_START_DATE.getPrefix();
    private static final String prefixEndDate = PREFIX_END_DATE.getPrefix();

    //@@author A0147990R
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {

        String inputprefix = parsePrefix(args);
        String pre;

        if (inputprefix.equals(EMPTY_STRING)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        Prefix inputPrefix;
        if (inputprefix.equals(prefixName)) {
            inputPrefix = PREFIX_NAME;
            pre = PREFIX_NAME.toString();
        } else if (inputprefix.equals(prefixInformation)) {
            inputPrefix = PREFIX_INFORMATION;
            pre = PREFIX_INFORMATION.toString();
        } else if (inputprefix.equals(prefixStartDate)) {
            inputPrefix = PREFIX_START_DATE;
            pre = PREFIX_START_DATE.toString();
        } else if (inputprefix.equals(prefixEndDate)) {
            inputPrefix = PREFIX_END_DATE;
            pre = PREFIX_END_DATE.toString();
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        try {
            ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(inputPrefix);
            argsTokenizer.tokenize(args);
            String keywords = argsTokenizer.getValue(inputPrefix).get();

            final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(keywords.trim());
            if (!matcher.matches()) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            return new FindCommand(pre, keywords);

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

    }

  //@@author A0147990R
    private String parsePrefix(String args) {
        int prefixIndex = args.indexOf("/");
        if (prefixIndex == -1) {
            return EMPTY_STRING;
        } else {
            return args.substring(1, prefixIndex + 1);
        }
    }

}
