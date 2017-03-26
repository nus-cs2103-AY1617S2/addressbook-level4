package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_UNDER;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.IncorrectCommand;
import seedu.doist.logic.commands.ListCommand;

public class ListCommandParser {
    private static final Pattern LIST_COMMAND_REGEX = Pattern.compile("(?<preamble>[^\\\\]*)" +
                                                                      "(?<parameters>((\\\\)(\\S+)(\\s+)([^\\\\]*))*)");

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = LIST_COMMAND_REGEX.matcher(args.trim());

        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        final String argument = matcher.group("preamble");
        final String parameters = matcher.group("parameters").trim();
        ArrayList<String> tokens = ParserUtil.getParameterKeysFromString(parameters);

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_UNDER);

        if (!argsTokenizer.validateTokens(tokens)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        argsTokenizer.tokenize(parameters);

        try {
            return new ListCommand(argument, argsTokenizer.getTokenizedArguments());
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
