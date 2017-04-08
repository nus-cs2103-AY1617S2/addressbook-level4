package seedu.doist.model;

import java.util.ArrayList;
import java.util.Map;

//@@author A0140887W
/**
 * Unmodifiable map of an alias list mapping
 */
public interface ReadOnlyAliasListMap {

    /**
     * Returns an unmodifiable map of an alias list mapping.
     */
    Map<String, ArrayList<String>> getAliasListMapping();

}
