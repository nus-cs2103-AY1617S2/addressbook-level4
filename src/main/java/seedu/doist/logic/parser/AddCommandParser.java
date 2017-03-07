package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.doist.logic.parser.CliSyntax.PREFIX_AS;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_EVERY;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_REMIND;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_UNDER;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.logic.commands.AddCommand;
import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private static final Pattern Add_Command_Regex = Pattern.compile("(?<preamble>[^\\\\]*)" +
                                                                     "(?<parameters>((\\\\)(\\S+)(\\s+)([^\\\\]*))*)");

    ////argsTokenizer.getValue(PREFIX_PHONE).get() is an an example of how to use the tokenizer
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = Add_Command_Regex.matcher(args.trim());
        if (!matcher.matches() || args.trim().equals("")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        final String argument = matcher.group("preamble");
        final String parameters = matcher.group("parameters").trim();
        ArrayList<String> tokens = new ArrayList<String>();
        for (int i = 0; i < parameters.length(); i++) {
            if (parameters.charAt(i) == '\\') {
                int start = i;
                while (parameters.charAt(i) != ' ') {
                    i++;
                }
                tokens.add(parameters.substring(start, i));
            }
        }

        ArgumentTokenizer argsTokenizer =
              new ArgumentTokenizer(PREFIX_FROM, PREFIX_TO, PREFIX_REMIND, PREFIX_EVERY,
                                    PREFIX_AS, PREFIX_BY, PREFIX_UNDER);

        if (!argsTokenizer.validateTokens(tokens)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        argsTokenizer.tokenize(parameters);

        try {
            return new AddCommand(argument, argsTokenizer.getTokenizedArguments());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
