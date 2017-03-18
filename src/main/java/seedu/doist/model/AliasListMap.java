package seedu.doist.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Stores a hashmap of arraylists representing the alias list
 */
public class AliasListMap implements ReadOnlyAliasListMap {

    private HashMap<String, ArrayList<String>> commandAliases;

    public void setDefaultCommandWords() {
        commandAliases = new HashMap<String, ArrayList<String>>();
        commandAliases.put("add",  new ArrayList<>(Arrays.asList("do")));
        commandAliases.put("clear",  new ArrayList<>());
        commandAliases.put("delete",  new ArrayList<>(Arrays.asList("del")));
        commandAliases.put("edit",  new ArrayList<>(Arrays.asList("update")));
        commandAliases.put("exit",  new ArrayList<>());
        commandAliases.put("find",  new ArrayList<>());
        commandAliases.put("finish",  new ArrayList<>(Arrays.asList("fin")));
        commandAliases.put("help",  new ArrayList<>());
        commandAliases.put("list",  new ArrayList<>(Arrays.asList("ls")));
        commandAliases.put("select",  new ArrayList<>());
        commandAliases.put("sort",  new ArrayList<>(Arrays.asList("sort_by")));
        commandAliases.put("unfinish",  new ArrayList<>(Arrays.asList("unfin")));
        commandAliases.put("alias",  new ArrayList<>());
        commandAliases.put("reset_alias",  new ArrayList<>());
        commandAliases.put("view_alias",  new ArrayList<>(Arrays.asList("list_alias", "ls_alias")));
    }

    public Set<String> getDefaultCommandWordSet() {
        if (commandAliases == null) {
            setDefaultCommandWords();
        }
        return commandAliases.keySet();
    }

    @Override
    public Map<String, ArrayList<String>> getAliasListMapping() {
        return Collections.unmodifiableMap(commandAliases);
    }

    public ArrayList<String> getAliasList(String defaultCommandWord) {
        if (commandAliases == null) {
            setDefaultCommandWords();
        }
        return commandAliases.get(defaultCommandWord);
    }

    public void setAlias(String alias, String commandWord) {
        assert(commandAliases.get(commandWord) != null);
        if (commandAliases == null) {
            setDefaultCommandWords();
        }
        for (String word : commandAliases.keySet()) {
            commandAliases.get(word).remove(alias);
        }
        ArrayList<String> aliases = commandAliases.get(commandWord);
        aliases.add(alias);
        commandAliases.replace(commandWord, aliases);
    }

    public AliasListMap() {
        setDefaultCommandWords();
    }

    public AliasListMap(ReadOnlyAliasListMap src) {
        commandAliases = new HashMap<String, ArrayList<String>>(src.getAliasListMapping());
    }

//// util methods

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
