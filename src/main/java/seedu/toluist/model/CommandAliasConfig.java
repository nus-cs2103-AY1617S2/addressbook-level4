package seedu.toluist.model;

import java.util.*;

/**
 * A model to manage alias
 */
public class CommandAliasConfig {
    private HashMap<String, String> aliasMapping = new HashMap<>();
    private Set<String> reservedKeywords = new HashSet<>();

    /**
     * Set alias for a command. Returns success status
     * @param alias
     * @param command
     * @return true / false
     */
    public boolean setAlias(String alias, String command) {
        if (reservedKeywords.contains(alias)) {
            return false;
        }

        aliasMapping.put(alias, command);
        return true;
    }

    /**
     * Returns the matching command for an alias
     * Returns Option.empty if no such alias exists
     * @param alias
     * @return optional string
     */
    public Optional<String> getCommand(String alias) {
        String command = aliasMapping.get(alias);

        if (command == null) {
            return Optional.empty();
        } else {
            return Optional.of(command);
        }
    }
}
