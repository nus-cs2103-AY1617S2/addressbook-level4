package seedu.address.logic;

import java.util.List;

import seedu.address.model.datastructure.Trie;

/**
 * Handles the autocompletion of the inpur
 */
public class Autocomplete {
    public static String[] autocompleteData = {"help", "add", "by", "repeat", "list"
            , "edit", "find", "delete", "select", "book"
            , "confirm", "editlabel", "undo", "clear", "push"
            , "pull", "export", "exit", "to", "on"
            , "hourly", "daily", "weekly", "monthly", "yearly"
            , "overdue", "outstanding", "completed", "today", "yesterday"
            , "tomorrow", "bookings"};
    private Trie trie;

    /**
     * Initializes auto-complete object with default auto-complete data
     */
    public Autocomplete() {
        trie = new Trie();
        trie.load(autocompleteData);
    }

    /**
     * Initializes auto-complete object with specified auto-complete data
     */
    public Autocomplete(String... data) {
        trie = new Trie();
        trie.load(data);
    }

    /**
     * Adds more strings for auto completion
     * @param phrases
     */
    public void addData(String... phrases) {
        trie.load(phrases);
    }

    /**
     * Find auto-complete suggestions given a search term
     * @param term - search term
     * @return a list of suggestions for the given term
     */
    public List<String> getSuggestions(String term) {
        return trie.findCompletions(term);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Autocomplete) {
            Autocomplete compare = (Autocomplete) other;
            return getSuggestions("").containsAll(compare.getSuggestions(""));
        } else {
            return false;
        }
    }

}
