//@@author A0114395E
package seedu.address.logic.parser;

import java.util.List;

import com.joestelmach.natty.DateGroup;

public class NattyParser {
    private static NattyParser instance = null;
    // Exists only to defeat instantiation.
    protected NattyParser() {
    }

    // Returns the singleton instance
    public static NattyParser getInstance() {
        if (instance == null) {
            instance = new NattyParser();
        }
        return instance;
    }


    /**
     * Parses, analyses and converts 'rich text' into a timestamp
     *
     * @param String - e.g. 'Tomorrow'
     * @return String - Timestamp
     */
    public String parseNLPDate(String argsString) {
        com.joestelmach.natty.Parser nParser = new com.joestelmach.natty.Parser();
        List<DateGroup> groups = nParser.parse(argsString);
        String output = "";
        for (DateGroup group : groups) {
            output = group.getDates().get(0).toString().replace("CST", "");
        }
        return output;
    }
}
