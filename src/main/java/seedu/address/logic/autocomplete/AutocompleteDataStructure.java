package seedu.address.logic.autocomplete;

import java.util.List;

//@@author A0140042A
/**
 * Interface for data structures for auto complete manager
 */
public interface AutocompleteDataStructure {

    /**
     * Loads phrases used for auto completion into the data structure
     */
    public void load(String... phrases);

    /**
     * Finds a list of completions that can be made using the current prefix
     * @param prefix - to match against
     * @return a list of strings with auto completed suggestions
     */
    public List<String> findCompletions(String prefix);
}
