package w10b3.todolist.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;

public class FocusListener implements ChangeListener<Boolean> {

    private AutoCompleteTextField textField;
    private ContextMenu suggestionsList;

    public FocusListener(AutoCompleteTextField autoCompleteTextField, ContextMenu suggestionsListMenu) {
        textField = autoCompleteTextField;
        suggestionsList = suggestionsListMenu;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean isFocusedPrev,
                        Boolean isFocusedNow) {
        if (isFocusedNow == false && suggestionsList.isShowing() == true) {
            suggestionsList.hide();
        } else if (isFocusedNow == true && isFocusedPrev == true) {
            suggestionsList.show(textField, Side.BOTTOM, textField.getCaretPosition(), 0);
        } else {
            suggestionsList.hide();
        }
    }

}
