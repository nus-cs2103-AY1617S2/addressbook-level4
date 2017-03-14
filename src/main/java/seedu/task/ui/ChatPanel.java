package seedu.task.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.ui.NewResultAvailableEvent;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.model.task.Chat;
import seedu.task.model.task.ChatList;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ChatPanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ChatPanel.class);
    private static final String FXML = "ChatPanel.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    private ChatList chatList;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextArea resultDisplay;

    @FXML
    private ListView<Chat> chatListView;

    public ChatPanel(AnchorPane anchorPane, ChatList chatList) {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        this.chatList = chatList;
        FxViewUtil.applyAnchorBoundaryParameters(resultDisplay, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);

        setConnections(chatList);
        anchorPane.getChildren().add(mainPane);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ChatList chatList) {
        chatListView.setItems(chatList.asObservableList());
        chatListView.setCellFactory(listView -> new ChatListViewCell());
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayed.setValue(event.message);
        chatList.add(new Chat(event.message));
    }


    class ChatListViewCell extends ListCell<Chat> {

        @Override
        protected void updateItem(Chat chat, boolean empty) {
            super.updateItem(chat, empty);

            if (empty || chat == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ChatMessage(chat).getRoot());
            }
        }
    }
}
