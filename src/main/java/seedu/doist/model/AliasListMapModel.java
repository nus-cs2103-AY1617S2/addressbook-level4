package seedu.doist.model;

import java.util.List;
import java.util.Set;

//@@author A0140887W
/**
 * The API of the AliasListMapModel component.
 */
public interface AliasListMapModel {
    /** Returns the AliasListMap */
    ReadOnlyAliasListMap getAliasListMap();

    /** Sets an alias in the AliasListMap */
    void setAlias(String alias, String commandWord);

    /** Get the alias list of a defaultCommandWord */
    List<String> getAliasList(String defaultCommandWord);

    /** Get the valid command list of a defaultCommandWord */
    List<String> getValidCommandList(String defaultCommandWord);

    /** Get the set of default command words */
    Set<String> getDefaultCommandWordSet();

    /** Resets alias list to default */
    void resetToDefaultCommandWords();

    /** Remove the alias if it exists, otherwise nothing happens */
    void removeAlias(String alias);
}
