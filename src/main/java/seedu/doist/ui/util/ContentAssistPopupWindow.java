package seedu.doist.ui.util;

import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

//@@author A0147980U
/**
 * A window that displays the list that contains the list of word completion suggestions
 * The position of the window will update with the cursor
 *
 */
public class ContentAssistPopupWindow extends ContextMenu {
    public ContentAssistPopupWindow() {
        super();
        setEventDispatcher(new ConsumeEventDispatch());
    }

    public void show(Node inputBox) {
        this.show(inputBox, this.getAnchorX(), this.getAnchorY());
    }

    /**
     * replace the current displayed items with the input ones
     * @param stringItems
     */
    public void replaceItems(ArrayList<String> stringItems) {
        getItems().clear();

        for (int i = 0; i < stringItems.size(); i++) {
            getItems().add(i, new ContentAssistSuggestionItem(stringItems.get(i)));
        }
    }
}

/**
 * Represents each item in ContentAssistPopupWindow
 */
class ContentAssistSuggestionItem extends MenuItem {
    private ContentAssistSuggestionItem() {
        super();
        setMnemonicParsing(false);
    }

    public ContentAssistSuggestionItem(String content) {
        this();
        this.setText(content);
    }
}

class ConsumeEventDispatch implements EventDispatcher {
    @Override
    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        return null;
    }
}

