package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Collection;

import seedu.doist.logic.commands.exceptions.CommandException;

//@@author A0147980U
/**
 * Remove the specified alias, if it exists
 */
public class RemoveAliasCommand extends Command {
    public static final String DEFAULT_COMMAND_WORD = "remove_alias";

    public static final String MESSAGE_USAGE = DEFAULT_COMMAND_WORD + ": Remove the alias if it exists\n"
            + "Parameters: ALIAS\n"
            + "Example: " + DEFAULT_COMMAND_WORD + " del";

    public static final String MESSAGE_SUCCESS = "Alias \"%1$s\" is removed";
    public static final String MESSAGE_INPUT_NOT_ALIAS = "\"%1$s\" is not an alias";

    private final String alias;

    public RemoveAliasCommand(String alias) {
        this.alias = alias;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Collection<ArrayList<String>> allAliasLists = aliasModel.getAliasListMap().getAliasListMapping().values();
        for (ArrayList<String> aliasList : allAliasLists) {
            if (aliasList.contains(alias)) {
                aliasModel.removeAlias(alias);
                return new CommandResult(String.format(MESSAGE_SUCCESS, alias));
            }
        }
        throw new CommandException(String.format(MESSAGE_INPUT_NOT_ALIAS, alias));
    }
}
