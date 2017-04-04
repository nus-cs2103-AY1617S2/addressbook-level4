package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.logic.commands.AliasCommand;
import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.IncorrectCommand;
import seedu.doist.logic.commands.RemoveAliasCommand;


//@@author A0147980U
public class RemoveAliasCommandParser {
    private static final Pattern ALIAS_COMMAND_REGEX = Pattern.compile("(?<preamble>[^\\\\]*)" +
                                                                      "(?<parameters>((\\\\)(\\S+)(\\s+)([^\\\\]*))*)");
    public static final String MESSAGE_ALIAS_NOT_SPECIFIED = "Alias must be specified.";
    public static final String MESSAGE_ALIAS_FORMAT_INVALID = "Alias can only contain alphabet"
                                                                    + "and underscores. \n%1$s";

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveAliasCommand
     * and returns an RemoveAliasCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = ALIAS_COMMAND_REGEX.matcher(args.trim());

        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }
        final String preamble = matcher.group("preamble").trim();

        try {
            if ("".equals(preamble)) {
                throw new IllegalValueException(MESSAGE_ALIAS_NOT_SPECIFIED);
            }
            if (!preamble.matches("[a-zA-Z_]+")) {
                throw new IllegalValueException(String.format(MESSAGE_ALIAS_FORMAT_INVALID,
                                                RemoveAliasCommand.MESSAGE_USAGE));
            }
            return new RemoveAliasCommand(preamble);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
