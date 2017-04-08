package seedu.doist.ui.util;

import java.util.ArrayList;

import org.fxmisc.richtext.InlineCssTextArea;

import javafx.geometry.Point2D;
import seedu.doist.logic.Logic;
import seedu.doist.logic.parser.ArgumentTokenizer.Prefix;
import seedu.doist.logic.parser.CliSyntax;


//@@author A0147980U
public class CommandAutoCompleteManager {
    private static CommandAutoCompleteManager instance;
    private boolean isFind = false;

    // relative to cursor center
    private final Point2D suggestionBoxOffset = new Point2D(-8, 12);
    private final int maxItemNu = 8;

    // for singleton pattern
    public static CommandAutoCompleteManager getInstance() {
        if (instance == null) {
            instance = new CommandAutoCompleteManager();
        }
        return instance;
    }

    // get the list of suggestions according to the input then display it
    public void suggestCompletion(InlineCssTextArea commandTextField, Logic logic) {
        attachSuggestionWindowIfNecessary(commandTextField);

        int cursorPosition = commandTextField.getCaretPosition();
        String[] words = commandTextField.getText(0, cursorPosition).split(" +", -1);
        String lastWord = words[words.length - 1];  // -1 means trailing space will NOT be discarded
        isFindCommand(words);
        if (!"".equals(lastWord)) {
            if (isFind) {
                displaySuggestions(commandTextField, getSuggestionsForSearch(words, logic));
            } else {
                displaySuggestions(commandTextField, getSuggestions(lastWord, logic));
            }
        } else {
            commandTextField.getPopupWindow().hide();
        }
    }

    private ArrayList<String> getSuggestions(String lastWord, Logic logic) {
        // TODO: make this method more "powerful"
        // handle different cases (command word, key, search history) differently
        // make better suggestion by using a queue to store history and store the frequency
        int count = 0;
        ArrayList<String> suggestions = new ArrayList<>();
        for (String commandWord : logic.getAllCommandWords()) {
            if (commandWord.startsWith(lastWord) && count < maxItemNu) {
                suggestions.add(commandWord);
                count++;
            }
        }
        for (Prefix prefix : CliSyntax.ALL_PREFICES) {
            if (prefix.toString().startsWith(lastWord) && count < maxItemNu) {
                suggestions.add(prefix.toString());
                count++;
            }
        }
        return suggestions;
    }

    /**
     * display the suggested text in a ContextMenu pop-up window
     */
    private void displaySuggestions(InlineCssTextArea commandTextField, ArrayList<String> suggestions) {
        ContentAssistPopupWindow suggestionList = (ContentAssistPopupWindow) commandTextField.getPopupWindow();
        suggestionList.replaceItems(suggestions);

        if (!commandTextField.getPopupWindow().isShowing()) {
            suggestionList.show(commandTextField);
        }
    }

    public void attachSuggestionWindowIfNecessary(InlineCssTextArea commandTextField) {
        if (commandTextField.getPopupWindow() == null) {
            commandTextField.setPopupWindow(new ContentAssistPopupWindow());
            commandTextField.setPopupAnchorOffset(suggestionBoxOffset);
        }
    }

    //@@author A0147620L
    /**
     * Method to provide auto-complete suggestions for search
     * @param words
     * @param logic
     * @return list of strings that represent possible autocomplete matches
     */
    private ArrayList<String> getSuggestionsForSearch(String[] words, Logic logic) {
        int count = 0;
        StringBuilder s = new StringBuilder();
        for (int i = 1; i < words.length; i++) {
            s.append(words[i]).append(" ");
        }
        ArrayList<String> suggestions = new ArrayList<>();
        if (s.toString().trim().isEmpty()) {
            return suggestions;
        }
        for (String desc : logic.getAllNames()) {
            if ((desc.toLowerCase().contains(s.toString().toLowerCase())) && count < maxItemNu) {
                suggestions.add(desc);
                count++;
            }
        }

        return suggestions;
    }

    private void isFindCommand(String[] words) {
        if (words.length > 0) {
            isFind = "find".equals(words[0]);
        } else {
            isFind = false;
        }
    }
    //@@author
}

