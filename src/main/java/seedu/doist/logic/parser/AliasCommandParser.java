package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_FOR;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.logic.commands.AliasCommand;
import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.IncorrectCommand;


public class AliasCommandParser {
    private static final Pattern ALIAS_COMMAND_REGEX = Pattern.compile("(?<preamble>[^\\\\]*)" +
                                                                      "(?<parameters>((\\\\)(\\S+)(\\s+)([^\\\\]*))*)");

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = ALIAS_COMMAND_REGEX.matcher(args.trim());

        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }
        final String argument = matcher.group("preamble").trim();
        final String parameters = matcher.group("parameters").trim();
        ArrayList<String> tokens = ParserUtil.getParameterKeysFromString(parameters);

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_FOR);

        if (!argsTokenizer.validateTokens(tokens)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }
        argsTokenizer.tokenize(parameters);

        try {
            List<String> parameter = argsTokenizer.getTokenizedArguments().get(PREFIX_FOR.toString());
            if (parameter == null || parameter.isEmpty()) {
                throw new IllegalValueException("command word must be specified");
            }
            String commandWord = parameter.get(0);
            return new AliasCommand(argument, commandWord);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
