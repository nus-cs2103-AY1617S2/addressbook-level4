package seedu.doist.logic.commands;


//@@author A0147980U
/**
 * Reset aliases to the default.
 */
public class ResetAliasCommand extends Command {

    public static final String DEFAULT_COMMAND_WORD = "reset_alias";

    public static final String MESSAGE_RESET_ALIAS_SUCCESS = "All aliases reset to default!";

    @Override
    public CommandResult execute() {
        aliasModel.resetToDefaultCommandWords();
        return new CommandResult(MESSAGE_RESET_ALIAS_SUCCESS);
    }
}
