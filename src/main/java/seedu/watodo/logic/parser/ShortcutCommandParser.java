package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.IncorrectCommand;
import seedu.watodo.logic.commands.ShortcutCommand;

//@@author A0143076J
/**
 * Parses input arguments and creates a new ShortcutCommand object
 */
public class ShortcutCommandParser {

    private final Pattern SHORTCUT_ARGS_REGEX =
            Pattern.compile("(?<Operation>\\S)(\\s+)(?<CommandWord>\\S+)(\\s+)(?<ShortcutKey>\\S+)");

    /**
     * Parses the given {@code String} of arguments in the context of the ShortcutCommand
     * and returns an ShortcutCommand object for execution.
     */
    public Command parse(String args) {
        try {
            final Matcher matcher = SHORTCUT_ARGS_REGEX.matcher(args.trim());
            if (!matcher.matches()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ShortcutCommand.MESSAGE_USAGE));
            }
            return new ShortcutCommand(matcher.group("Operation"), matcher.group("CommandWord"),
                    matcher.group("ShortcutKey"));
        } catch (IllegalValueException e) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, e.getMessage()));
        }
    }

}
