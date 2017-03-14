package seedu.address.logic.autocomplete;

import java.util.List;

public interface Autocomplete {

    public static String[] AUTOCOMPLETE_DATA = {"help", "add", "by", "repeat", "list"
            , "edit", "find", "delete", "select", "book"
            , "confirm", "editlabel", "undo", "clear", "push"
            , "pull", "export", "exit", "to", "on"
            , "hourly", "daily", "weekly", "monthly", "yearly"
            , "overdue", "outstanding", "completed", "today", "yesterday"
            , "tomorrow", "bookings"};

    /**
     * Adds more strings for auto completion
     * @param phrases
     */
    void addData(String... phrases);

    /**
     * Find auto-complete suggestions given a search term
     * @param term - search term
     * @return a list of suggestions for the given term
     */
    List<String> getSuggestions(String term);

    /**
     * Returns the character index at which the characters in the list of suggestions start to differ
     */
    int getCommonSubstringEndIndexFromStart(List<String> suggestions);

    /**
     * Returns the longest string in the list, empty string otherwise
     */
    String getLongestString(List<String> suggestions);

    /**
     * Extracts the word at the current the caretPosition in commandTextField
     * The caret always represents the character on the left of it
     */
    String getWordAtCursor(String command, int caretPosition);

    /**
     * Gets the start index of the word at the caretPosition in commandTextField
     */
    int getStartIndexOfWordAtCursor(String command, int caretPosition);

    /**
     * Gets the end index of the word at the caretPosition in commandTextField
     */
    int getEndIndexOfWordAtCursor(String command, int caretPosition);

    /**
     * replace the current word with the suggestion provided
     */
    String replaceCurrentWordWithSuggestion(String command, int caretPosition, String suggestion, String toAppend);

    boolean equals(Object other);

}
