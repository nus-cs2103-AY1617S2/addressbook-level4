package seedu.doist.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.fxmisc.richtext.InlineCssTextArea;

import seedu.doist.model.Model;

public class CommandAutoCompleteManager {
    private static CommandAutoCompleteManager instance;
    private static final String SUGGESTION_STYLE = "-fx-fill: grey;";

    private boolean hasTriggered = false;

    // for singleton pattern
    protected static CommandAutoCompleteManager getInstance() {
        if (instance == null) {
            instance = new CommandAutoCompleteManager();
        }
        return instance;
    }

    // main function method
    public void suggestCompletion(InlineCssTextArea commandTextField, Model model) {
        // prevent "recursive" call since this method will trigger change event,
        // which, again, will call this method
        if (hasTriggered) {
            return;
        }

        hasTriggered = true;
        removeSuggestionAfterCursor(commandTextField);

        String[] words = commandTextField.getText().split(" +");
        String lastWord = words[words.length - 1];
        if (!lastWord.equals("")) {
            displaySuggestion(commandTextField, lastWord, getSuggestion(lastWord, model));
        }
        hasTriggered = false;
    }

    private String getSuggestion(String lastWord, Model model) {
        //TODO: make this method more "powerful"
        // handle different cases (command word, key, search history) differently
        // make better suggestion by using a queue to store history and store the frequency
        List<String> allCommandWords = getAllCommandWords(model);
        for (String commandWord : allCommandWords) {
            if (commandWord.startsWith(lastWord)) {
                return commandWord;
            }
        }
        return "";
    }

    /**
     * display the suggested text in light grey
     */
    private void displaySuggestion(InlineCssTextArea commandTextField, String lastWord, String suggestedWord) {
        if (lastWord == suggestedWord) {
            return;
        }
        String remainingWord = suggestedWord.replace(lastWord, "");
        int previousLength = commandTextField.getLength();
        commandTextField.appendText(remainingWord);
        commandTextField.setStyle(previousLength, previousLength + remainingWord.length(), SUGGESTION_STYLE);
        commandTextField.moveTo(previousLength);
    }

    private void removeSuggestionAfterCursor(InlineCssTextArea commandTextField) {
        int cursorPosition = commandTextField.getCaretPosition();
        commandTextField.deleteText(cursorPosition, commandTextField.getLength());
    }

    private List<String> getAllCommandWords(Model model) {
        ArrayList<String> allCommandWords = new ArrayList<String>();
        Set<String> allDefaultCommandWords = model.getDefaultCommandWordSet();
        for (String defaultCommandWords : allDefaultCommandWords) {
            allCommandWords.addAll(model.getValidCommandList(defaultCommandWords));
        }
        return allCommandWords;
    }
}
