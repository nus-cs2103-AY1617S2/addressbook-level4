package seedu.address.logic.autocomplete;

import java.util.List;

import seedu.address.model.datastructure.Trie;

/**
 * Handles the auto-completion of the input
 */
public class AutocompleteTrie implements Autocomplete {
    private Trie trie;

    /**
     * Initializes auto-complete object with default auto-complete data
     */
    public AutocompleteTrie() {
        trie = new Trie();
        trie.load(AUTOCOMPLETE_DATA);
    }

    /**
     * Initializes auto-complete object with specified auto-complete data
     */
    public AutocompleteTrie(String... data) {
        trie = new Trie();
        trie.load(data);
    }

    @Override
    public void addData(String... phrases) {
        trie.load(phrases);
    }

    @Override
    public List<String> getSuggestions(String term) {
        return trie.findCompletions(term);
    }

    //Command utilities to get word/start index/end index/replace position at current caret position

    @Override
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

    @Override
    public String getLongestString(List<String> suggestions) {
        String longest = "";
        for (String suggestion : suggestions) {
            if (suggestion.length() >= longest.length()) {
                longest = suggestion;
            }
        }
        return longest;
    }

    @Override
    public String getWordAtCursor(String command, int caretPosition) {
        if (command != null && !command.trim().equals("")) {
            int startIndex = getStartIndexOfWordAtCursor(command, caretPosition);
            int endIndex = getEndIndexOfWordAtCursor(command, caretPosition);
            return command.substring(startIndex, endIndex);
        } else {
            return command;
        }

    }

    @Override
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

    @Override
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

    @Override
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
        if (other instanceof AutocompleteTrie) {
            Autocomplete compare = (Autocomplete) other;
            return getSuggestions("").containsAll(compare.getSuggestions(""));
        } else {
            return false;
        }
    }

}
