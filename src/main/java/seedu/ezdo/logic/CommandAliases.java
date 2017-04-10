//@@author A0138907W
package seedu.ezdo.logic;

import static seedu.ezdo.commons.util.CommandUtil.isExistingCommand;

import java.io.Serializable;
import java.util.HashMap;

import seedu.ezdo.commons.exceptions.AliasAlreadyInUseException;
import seedu.ezdo.commons.exceptions.CommandDoesNotExistException;

/**
 * Keeps track of the command aliases specified by user.
 */
public class CommandAliases implements Serializable {

    private HashMap<String, String> commandAliasesMap;

    /**
     * Creates a CommandAliases with no command aliases initially.
     */
    public CommandAliases() {
        commandAliasesMap = new HashMap<>();
    }

    /**
     * Adds a new alias for a command.
     *
     * @param command An existing command in ezDo to be aliased.
     * @param alias   The new alias for the given command.
     * @throws AliasAlreadyInUseException   If the alias is already in use by an existing ezDo command.
     * @throws CommandDoesNotExistException If the command to be aliased does not exist in ezDo.
     */
    public void addAlias(String command, String alias) throws AliasAlreadyInUseException, CommandDoesNotExistException {
        if (isExistingCommand(alias)) {
            throw new AliasAlreadyInUseException();
        }
        if (!isExistingCommand(command)) {
            throw new CommandDoesNotExistException();
        }
        commandAliasesMap.put(alias, command);
    }

    /**
     * Clears all existing aliases.
     */
    public void clearAliases() {
        commandAliasesMap = new HashMap<>();
    }

    /**
     * Checks if the given string is an alias for a command in ezDo.
     */
    public boolean checkIfAlias(String alias) {
        return commandAliasesMap.get(alias) != null;
    }

    /**
     * Gets the command that is aliased by the given alias.
     */
    public String getCommandFromAlias(String alias) {
        return commandAliasesMap.get(alias);
    }

}
