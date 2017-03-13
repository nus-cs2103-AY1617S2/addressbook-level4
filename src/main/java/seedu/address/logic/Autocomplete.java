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

    //Command utilities to get word/start index/end index/replace position at current caret position

    /**
     * Returns the character index at which the characters in the list of suggestions start to differ
     */
    public int getCommonSubstringEndIndexFromStart(List<String> suggestions) {
        String longestString = getLongestString(suggestions);
        int commonSubstringIndex = 0;
        char currentChar;
        commonSubstring:
        for (int charIndex = 0; charIndex < longestString.length(); charIndex++) {
            currentChar = longestString.charAt(charIndex);
            for (String suggestion : suggestions) {
                if (suggestion.length() <= charIndex || suggestion.charAt(charIndex) != currentChar) {
                    break commonSubstring;
                }
            }
            commonSubstringIndex++;
        }
        return commonSubstringIndex;
    }

    /**
     * Returns the longest string in the list, empty string otherwise
     */
    public String getLongestString(List<String> suggestions) {
        String longest = "";
        for (String suggestion : suggestions) {
            if (suggestion.length() >= longest.length()) {
                longest = suggestion;
            }
        }
        return longest;
    }

    /**
     * Extracts the word at the current the caretPosition in commandTextField
     * The caret always represents the character on the left of it
     */
    public String getWordAtCursor(String command, int caretPosition) {
        if (command != null && !command.trim().equals("")) {
            int startIndex = getStartIndexOfWordAtCursor(command, caretPosition);
            int endIndex = getEndIndexOfWordAtCursor(command, caretPosition);
            return command.substring(startIndex, endIndex);
        } else {
            return command;
        }

    }

    /**
     * Gets the start index of the word at the caretPosition in commandTextField
     */
    public int getStartIndexOfWordAtCursor(String command, int caretPosition) {
        int currentPosition = caretPosition;
        int startIndex;
        currentPosition = currentPosition - 1 < 0 ? 0 : currentPosition - 1;
        while (currentPosition > 0  &&
                currentPosition < command.length() &&
                !Character.isWhitespace(command.charAt(currentPosition))) {
            currentPosition--;
        }

        //Increment index by 1 if whitespace is met
        if (Character.isWhitespace(command.charAt(currentPosition))) {
            currentPosition++;
        }

        startIndex = currentPosition;
        return startIndex;
    }

    /**
     * Gets the end index of the word at the caretPosition in commandTextField
     */
    public int getEndIndexOfWordAtCursor(String command, int caretPosition) {
        int currentPosition = caretPosition;
        int endIndex;
        while (currentPosition >= 0  &&
                currentPosition < command.length() &&
                !Character.isWhitespace(command.charAt(currentPosition))) {
            currentPosition++;
        }
        endIndex = currentPosition;
        return endIndex;
    }

    /**
     * replace the current word with the suggestion provided
     */
    public String replaceCurrentWordWithSuggestion(String command, int caretPosition,
                                                    String suggestion, String toAppend) {
        StringBuffer commandAutocompleted = new StringBuffer(command);
        commandAutocompleted.replace(getStartIndexOfWordAtCursor(command, caretPosition),
                                getEndIndexOfWordAtCursor(command, caretPosition),
                                (suggestion + toAppend));
        return commandAutocompleted.toString();
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
