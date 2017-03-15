package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.task.model.task.Chat;

public class ChatMessage extends UiPart<Region> {

    private static final String FXML = "ChatMessage.fxml";

    @FXML
    private StackPane chatMessagePane;

    @FXML
    private Label message;

    public ChatMessage(Chat chat) {
        super(FXML);
        message.setText(chat.getMessage());
    }

}
