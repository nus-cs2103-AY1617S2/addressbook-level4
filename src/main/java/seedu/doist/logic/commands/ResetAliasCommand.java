package seedu.doist.logic.commands;

/**
 * Reset aliases to the default.
 */
public class ResetAliasCommand extends Command {

    public static final String DEFAULT_COMMAND_WORD = "reset_alias";

    public static final String MESSAGE_RESET_ALIAS_SUCCESS = "all aliases reset to the default";

    @Override
    public CommandResult execute() {
        Command.setDefaultCommandWords();
        return new CommandResult(MESSAGE_RESET_ALIAS_SUCCESS);
    }

    public static CommandInfo info() {
        return new CommandInfo(Command.getAliasList(DEFAULT_COMMAND_WORD), DEFAULT_COMMAND_WORD);
    }
}
