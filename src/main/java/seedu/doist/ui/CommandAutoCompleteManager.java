package seedu.doist.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.fxmisc.richtext.InlineCssTextArea;

import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import seedu.doist.model.Model;

//@@author A0147980U
public class CommandAutoCompleteManager {
    private static CommandAutoCompleteManager instance;

    // relative to cursor center
    private final Point2D suggestionBoxOffset = new Point2D(-8, 12);

    // for singleton pattern
    protected static CommandAutoCompleteManager getInstance() {
        if (instance == null) {
            instance = new CommandAutoCompleteManager();
        }
        return instance;
    }

    // main function method
    public void suggestCompletion(InlineCssTextArea commandTextField, Model model) {
        int cursorPosition = commandTextField.getCaretPosition();
        String[] words = commandTextField.getText(0, cursorPosition).split(" +", -1);
        String lastWord = words[words.length - 1];  // -1 means trailing space will NOT be discarded
        if (!lastWord.equals("")) {
            displaySuggestions(commandTextField, getSuggestions(lastWord, model));
        } else {
            commandTextField.getPopupWindow().hide();
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
     * display the suggested text in a ContextMenu pop-up window
     */
    private void displaySuggestions(InlineCssTextArea commandTextField, ArrayList<String> suggestions) {

        if (commandTextField.getPopupWindow() == null) {
            commandTextField.setPopupWindow(new ContextMenu());
            commandTextField.setPopupAnchorOffset(suggestionBoxOffset);
        }

        ContextMenu suggestionList = (ContextMenu) commandTextField.getPopupWindow();
        suggestionList.setEventDispatcher(new ConsumeEventDispatch());
        suggestionList.getItems().clear();

        for (int i = 0; i < suggestions.size(); i++) {
            MenuItem item = new MenuItem(suggestions.get(i));
            item.setMnemonicParsing(false);
            suggestionList.getItems().add(i, item);
        }

        if (!commandTextField.getPopupWindow().isShowing()) {
            commandTextField.getPopupWindow().show(commandTextField,
                                                   suggestionList.getAnchorX(),
                                                   suggestionList.getAnchorY());
        }
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

class ConsumeEventDispatch implements EventDispatcher {
    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        return null;
    }
}
