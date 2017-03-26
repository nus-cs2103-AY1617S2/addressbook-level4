package seedu.task.ui.chat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.task.model.chat.Chat;
import seedu.task.ui.UiPart;

public class SuruChatMessage extends UiPart<Region> {
    protected static final String FXML = "SuruChatMessage.fxml";

    @FXML
    private StackPane chatMessagePane;
    @FXML
    protected Label message;

    public SuruChatMessage(Chat chat) {
        super(FXML);
        message.setText(chat.getMessage());
    }

}
