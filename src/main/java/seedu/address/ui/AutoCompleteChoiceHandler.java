package seedu.address.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;

public class AutoCompleteChoiceHandler implements EventHandler<ActionEvent> {

    private String optionChosen, precedingText;
    private AutoCompleteTextField choiceHandlerHost;
    private ContextMenu suggestionsList;

    public AutoCompleteChoiceHandler(String commandString, String stringBeforePrefix, AutoCompleteTextField eventHost,
            ContextMenu dropDownList) {
        optionChosen = commandString;
        precedingText = stringBeforePrefix;
        choiceHandlerHost = eventHost;
        suggestionsList = dropDownList;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String output = precedingText.concat(optionChosen);
        choiceHandlerHost.setText(output);
        choiceHandlerHost.positionCaret(output.length());
        suggestionsList.hide();
    }
}
