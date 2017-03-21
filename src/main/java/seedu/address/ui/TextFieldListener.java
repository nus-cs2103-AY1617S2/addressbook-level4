package seedu.address.ui;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;

public class TextFieldListener implements ChangeListener<String> {

    private ContextMenu suggestionsList;
    private AutoCompleteTextField textField;

    public TextFieldListener(AutoCompleteTextField autoCompleteTextField, ContextMenu suggestionsListMenu) {
        suggestionsList = suggestionsListMenu;
        textField = autoCompleteTextField;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String prevTextInput,
            String currentTextInput) {
        if (currentTextInput.length() == 0) {
            suggestionsList.hide();
        } else {
            LinkedList<String> searchResults = new LinkedList<>();
            int lastPrefixIndex = getLastPrefixIndex(currentTextInput);
            String stringToCheck = getCommandInString(currentTextInput, lastPrefixIndex);
            String stringBeforePrefix = getTextInFrontOfCommand(currentTextInput, lastPrefixIndex);
            searchResults.addAll(
                    AutoCompleteTextField.PREFIXCOMMANDS.subSet(stringToCheck, stringToCheck + Character.MAX_VALUE));

            populatePopup(searchResults, stringBeforePrefix);
            suggestionsList.show(textField, Side.BOTTOM, textField.getCaretPosition(), 0);
        }
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

        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new AutoCompleteChoiceHandler(result, stringBeforePrefix, textField, suggestionsList));
            menuItems.add(item);
        }
        suggestionsList.getItems().clear();
        suggestionsList.getItems().addAll(menuItems);
    }

    private int getLastPrefixIndex(String currentText) {
        int indexOfPrefix = currentText.lastIndexOf(AutoCompleteTextField.PREFIXSYMBOL, currentText.length() - 1);
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

}
