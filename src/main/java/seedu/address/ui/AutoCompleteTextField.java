package seedu.address.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class is a TextField which implements an "auto complete" functionality,
 * based on a supplied list of entries.
 *
 * @author Caleb Brinkman
 */
public class AutoCompleteTextField extends TextField {
    /** The existing auto complete entries. */
    private final SortedSet<String> entries;
    /** The pop up used to select an entry. */
    private ContextMenu entriesPopup;

    /** Construct a new AutoCompleteTextField. */
    public AutoCompleteTextField() {
        super();
        entries = new TreeSet<>();
        initializeEntries();
        entriesPopup = new ContextMenu();
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String prevTextInput,
                    String currentTextInput) {
                if (currentTextInput.length() == 0) {
                    entriesPopup.hide();
                } else {
                    LinkedList<String> searchResult = new LinkedList<>();
                    int lastPrefixIndex = getLastPrefixIndex(currentTextInput);
                    String stringToCheck = getCommandInString(currentTextInput, lastPrefixIndex);
                    String stringBeforePrefix = getTextInFrontOfCommand(currentTextInput, lastPrefixIndex);
                    searchResult.addAll(entries.subSet(stringToCheck, stringToCheck + Character.MAX_VALUE));
                    if (entries.size() > 0) {
                        populatePopup(searchResult, stringBeforePrefix);
                        entriesPopup.show(AutoCompleteTextField.this, getCaretPosition(), 0);
                    } else {
                        entriesPopup.hide();
                    }
                }
            }
        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean,
                    Boolean aBoolean2) {
                entriesPopup.hide();
            }
        });

    }

    private int getLastPrefixIndex(String currentText) {
        int indexOfPrefix = currentText.lastIndexOf('@', currentText.length() - 1);
        int indexOfSpace = currentText.lastIndexOf(' ', currentText.length() - 1);
        if (indexOfPrefix == -1 || indexOfPrefix < indexOfSpace) {
            return -1;
        } else {
            return indexOfPrefix;
        }
    }

    private String getCommandInString(String currentText, int lastPrefixIndex) {
        String processedString = new String(" ");
        if (lastPrefixIndex != -1) {
            processedString = currentText.substring(lastPrefixIndex, currentText.length());
        }

        return processedString;
    }

    private String getTextInFrontOfCommand(String currentText, int lastPrefixIndex) {
        String stringBeforePrefix = new String("");
        if (lastPrefixIndex != -1) {
            stringBeforePrefix = currentText.substring(0, lastPrefixIndex);
        }

        return stringBeforePrefix;
    }

    private void initializeEntries() {
        entries.add("/venue ");
        entries.add("/from ");
        entries.add("/to ");
        entries.add("/level ");
        entries.add("/description ");
    }

    /**
     * Get the existing set of autocomplete entries.
     *
     * @return The existing autocomplete entries.
     */
    public SortedSet<String> getEntries() {
        return entries;
    }

    /**
     * Populate the entry set with the given search results. Display is limited
     * to 10 entries, for performance.
     *
     * @param searchResult
     *            The set of matching strings.
     */
    private void populatePopup(List<String> searchResult, String stringBeforePrefix) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String output = stringBeforePrefix.concat(result);
                    setText(output);
                    positionCaret(output.length());
                    entriesPopup.hide();
                }
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);

    }
}