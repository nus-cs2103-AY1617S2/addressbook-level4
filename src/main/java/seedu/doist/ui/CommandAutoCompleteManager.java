package seedu.doist.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.fxmisc.richtext.InlineCssTextArea;

import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import seedu.doist.model.Model;

public class CommandAutoCompleteManager {
    private static CommandAutoCompleteManager instance;
    private static final String SUGGESTION_STYLE = "-fx-fill: grey;";

    // for singleton pattern
    protected static CommandAutoCompleteManager getInstance() {
        if (instance == null) {
            instance = new CommandAutoCompleteManager();
        }
        return instance;
    }

    // main function method
    public void suggestCompletion(InlineCssTextArea commandTextField, Model model) {
        String[] words = commandTextField.getText().split(" +");
        String lastWord = words[words.length - 1];
        if (!lastWord.equals("")) {
            displaySuggestion(commandTextField, getSuggestions(lastWord, model));
        }
    }

    private ArrayList<String> getSuggestions(String lastWord, Model model) {
        //TODO: make this method more "powerful"
        // handle different cases (command word, key, search history) differently
        // make better suggestion by using a queue to store history and store the frequency

        ArrayList<String> suggestions = new ArrayList<>();
        List<String> allCommandWords = getAllCommandWords(model);
        for (String commandWord : allCommandWords) {
            if (commandWord.startsWith(lastWord)) {
                suggestions.add(commandWord);
            }
        }
        return suggestions;
    }

    /**
     * display the suggested text in light grey
     */
    private void displaySuggestion(InlineCssTextArea commandTextField, ArrayList<String> suggestedWords) {

        if (commandTextField.getPopupWindow() == null) {
            commandTextField.setPopupWindow(new ContextMenu());
        }
        ContextMenu suggestionList = (ContextMenu) commandTextField.getPopupWindow();
        commandTextField.setPopupAnchorOffset(new Point2D(-18, 18));
        suggestionList.getItems().clear();
        int index = 0;
        for (String word : suggestedWords) {
            MenuItem m = new MenuItem(word);
            suggestionList.getItems().add(index, m);
            index++;
        }
        if (commandTextField.getPopupWindow().isShowing()) {
            return;
        }
        commandTextField.getPopupWindow().show(commandTextField, suggestionList.getAnchorX(), suggestionList.getAnchorY());;;
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
