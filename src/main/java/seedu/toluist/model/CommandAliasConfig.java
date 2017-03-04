package seedu.toluist.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A model to manage alias
 */
public class CommandAliasConfig {
    private static CommandAliasConfig instance;

    public static CommandAliasConfig getInstance() {
        if (instance == null) {
            instance = new CommandAliasConfig();
        }
        return instance;
    }

    private HashMap<String, String> aliasMapping = new HashMap<>();
    private Set<String> reservedKeywords = new HashSet<>();

    public HashMap<String, String> getAliasMapping() {
        return aliasMapping;
    }

    /**
     * Set alias for a command. Returns success status
     * @param alias
     * @param command
     * @return true / false
     */
    public boolean setAlias(String alias, String command) {
        if (isReservedWord(alias)) {
            return false;
        }

        aliasMapping.put(alias, command);
        return true;
    }

    /**
     * Remove an alias. Return sucess status
     * @param alias
     * @return true / false
     */
    public boolean removeAlias(String alias) {
        aliasMapping.remove(alias);
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

    /**
     * Set reserved keywords
     * @param reservedKeywords
     */
    public void setReservedKeywords(Set<String> reservedKeywords) {
        this.reservedKeywords = reservedKeywords;
    }

    /**
     * Check if the alias is a reserved word
     * @param alias
     * @return true/false
     */
    public boolean isReservedWord(String alias) {
        return reservedKeywords.contains(alias);
    }

    /**
     * Check if argument is an alias
     * @param alias
     * @return true / false
     */
    public boolean isAlias(String alias) {
        return aliasMapping.containsKey(alias);
    }

    /**
     * Convert a command that possibly contains an alias prefix to one without
     * Recursive unpacking of alias is not supported
     * e.g if a is an alias for b, and b is an alias a, dealias(a) will return b
     * @param command
     * @return converted command
     */
    public String dealias(String command) {
        for (String alias : aliasMapping.keySet()) {
            if (command.startsWith(alias)) {
                return aliasMapping.get(alias) + command.substring(alias.length(), command.length());
            }
        }
        return command;
    }
}
