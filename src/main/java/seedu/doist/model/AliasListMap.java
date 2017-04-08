package seedu.doist.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//@@author A0140887W
/**
 * Stores a HashMap of ArrayLists representing the alias list
 */
public class AliasListMap implements ReadOnlyAliasListMap {

    private HashMap<String, ArrayList<String>> commandAliases;

    //@@author A0147980U
    /**
     * Initializes the entire HashMap for all aliases, resets to default
     */
    public void setDefaultAliasListMapping() {
        HashMap<String, ArrayList<String>> defaultCommandAliases = getDefaultAliasListMapping();
        commandAliases = new HashMap<String, ArrayList<String>>();
        for (String word : defaultCommandAliases.keySet()) {
            commandAliases.put(word, defaultCommandAliases.get(word));
        }
    }

    private HashMap<String, ArrayList<String>> getDefaultAliasListMapping() {
        HashMap<String, ArrayList<String>> aliasMap = new HashMap<String, ArrayList<String>>();
        aliasMap.put("add",  new ArrayList<>(Arrays.asList("do")));
        aliasMap.put("clear",  new ArrayList<>());
        aliasMap.put("delete",  new ArrayList<>(Arrays.asList("del")));
        aliasMap.put("edit",  new ArrayList<>(Arrays.asList("update")));
        aliasMap.put("exit",  new ArrayList<>());
        aliasMap.put("find",  new ArrayList<>());
        aliasMap.put("finish",  new ArrayList<>(Arrays.asList("fin")));
        aliasMap.put("help",  new ArrayList<>());
        aliasMap.put("list",  new ArrayList<>(Arrays.asList("ls")));
        aliasMap.put("load",  new ArrayList<>());
        aliasMap.put("save_at", new ArrayList<>(Arrays.asList("save")));
        aliasMap.put("select",  new ArrayList<>());
        aliasMap.put("sort",  new ArrayList<>(Arrays.asList("sort_by")));
        aliasMap.put("unfinish",  new ArrayList<>(Arrays.asList("unfin")));
        aliasMap.put("alias",  new ArrayList<>());
        aliasMap.put("remove_alias",  new ArrayList<>());
        aliasMap.put("reset_alias",  new ArrayList<>());
        aliasMap.put("view_alias",  new ArrayList<>(Arrays.asList("list_alias", "ls_alias")));
        aliasMap.put("undo",  new ArrayList<>());
        aliasMap.put("redo",  new ArrayList<>());
        return aliasMap;
    }

    /**
     * @return a set of strings which are the default command words
     */
    public Set<String> getDefaultCommandWordSet() {
        return getDefaultAliasListMapping().keySet();
    }

    @Override
    public Map<String, ArrayList<String>> getAliasListMapping() {
        return Collections.unmodifiableMap(commandAliases);
    }

    /**
     * Returns an unmodifiable list of the alias list of the specified default command word
     */
    public List<String> getAliasList(String defaultCommandWord) {
        return Collections.unmodifiableList(commandAliases.get(defaultCommandWord));
    }

    /**
     * Adds an alias to the alias list for that default command word
     * Also removes any occurrence of that alias in the alias list of other default command words
     */
    public void setAlias(String alias, String commandWord) {
        assert(commandAliases.get(commandWord) != null);
        if (commandAliases == null) {
            setDefaultAliasListMapping();
        }
        for (String word : commandAliases.keySet()) {
            commandAliases.get(word).remove(alias);
        }
        ArrayList<String> aliases = commandAliases.get(commandWord);
        aliases.add(alias);
        commandAliases.replace(commandWord, aliases);
    }

    /**
     * remove the specified alias from the corresponding command word if such alias exists
     * nothing happen it the input string is not an alias
     * @param alias
     */
    public void removeAlias(String alias) {
        for (String word : commandAliases.keySet()) {
            commandAliases.get(word).remove(alias);
        }
    }

    //@@author A0140887W
    public AliasListMap() {
        setDefaultAliasListMapping();
    }

    public AliasListMap(ReadOnlyAliasListMap src) {
        commandAliases = new HashMap<String, ArrayList<String>>(src.getAliasListMapping());
        if (commandAliases.size() < getDefaultAliasListMapping().size()) {
            setDefaultAliasListMapping();
        }
    }

    // utility methods

    @Override
    public String toString() {
        return commandAliases.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AliasListMap // instanceof handles nulls
                && this.getAliasListMapping().equals((((AliasListMap) other).getAliasListMapping())));
    }

    @Override
    public int hashCode() {
        return commandAliases.hashCode();
    }
}
