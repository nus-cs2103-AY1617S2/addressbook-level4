package seedu.doist.ui.util;

import java.util.ArrayList;

import org.fxmisc.richtext.InlineCssTextArea;

import javafx.geometry.Point2D;
import seedu.doist.logic.Logic;

//@@author A0147980U
public class CommandAutoCompleteManager {
    private static CommandAutoCompleteManager instance;

    // relative to cursor center
    private final Point2D suggestionBoxOffset = new Point2D(-8, 12);

    // for singleton pattern
    public static CommandAutoCompleteManager getInstance() {
        if (instance == null) {
            instance = new CommandAutoCompleteManager();
        }
        return instance;
    }

    // main function method
    public void suggestCompletion(InlineCssTextArea commandTextField, Logic logic) {
        attachSuggestionWindowIfNecessary(commandTextField);

        int cursorPosition = commandTextField.getCaretPosition();
        String[] words = commandTextField.getText(0, cursorPosition).split(" +", -1);
        String lastWord = words[words.length - 1];  // -1 means trailing space will NOT be discarded
        if (!lastWord.equals("")) {
            displaySuggestions(commandTextField, getSuggestions(lastWord, logic));
        } else {
            commandTextField.getPopupWindow().hide();
        }
    }

    private ArrayList<String> getSuggestions(String lastWord, Logic logic) {
        // TODO: make this method more "powerful"
        // handle different cases (command word, key, search history) differently
        // make better suggestion by using a queue to store history and store the frequency

        ArrayList<String> suggestions = new ArrayList<>();
        for (String commandWord : logic.getAllCommandWords()) {
            if (commandWord.startsWith(lastWord)) {
                suggestions.add(commandWord);
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
}

