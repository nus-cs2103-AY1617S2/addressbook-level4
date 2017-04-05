package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.commandmap.CommandMap.BaseCommandNotAllowedAsAliasException;
import seedu.task.model.commandmap.CommandMap.OriginalCommandNotFoundException;

public class AliasCommand extends Command {

    public static final String COMMAND_WORD = "use";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Specifies a keyword to use with a command.\n"
            + "Parameters: ALIAS for ORIGINAL\n"
            + "Command words ALIAS and ORIGINAL must be single alphabetical strings without spaces.\n"
            + "Example: " + COMMAND_WORD + " create for add";
    public static final String MESSAGE_ADD_ALIAS_SUCCESS = "\"%1$s\" can now be used as %2$s command.";
    public static final String MESSAGE_INVALID_ORIGNINAL_COMMAND_STRING = "The original command is invalid!";
    public static final String MESSAGE_INVALID_ALIAS_COMMAND_STRING = "The alias command is invalid!";
    public static final String MESSAGE_ALIAS_EQUALS_ORIGINAL_NOT_ALLOWED =
            "The alias cannot be the same as the original command word!";
    public static final String COMMAND_STRINGS_VALIDATION_REGEX =
            "(?<alias>([a-zA-Z]+))"
            + "(\\s+)(for)(\\s+)"
            + "(?<original>([a-zA-Z]+))";

    private final String originalCommandString;
    private final String aliasCommandString;

    public AliasCommand(String alias, String original) {
        this.aliasCommandString = alias;
        this.originalCommandString = original;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.addCommandAlias(aliasCommandString, originalCommandString);
            return new CommandResult(String.format(MESSAGE_ADD_ALIAS_SUCCESS,
                    aliasCommandString,
                    originalCommandString));
        } catch (OriginalCommandNotFoundException e) {
            throw new CommandException(MESSAGE_INVALID_ORIGNINAL_COMMAND_STRING);
        } catch (BaseCommandNotAllowedAsAliasException e) {
            throw new CommandException(MESSAGE_ALIAS_EQUALS_ORIGINAL_NOT_ALLOWED);
        }
    }
}
