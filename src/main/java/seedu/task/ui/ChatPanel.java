package seedu.task.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.ui.NewResultAvailableEvent;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.model.Sender;
import seedu.task.model.chat.Chat;
import seedu.task.model.chat.ChatList;
import seedu.task.ui.chat.SuruChatMessage;
import seedu.task.ui.chat.UserChatMessage;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ChatPanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ChatPanel.class);
    private static final String FXML = "ChatPanel.fxml";
    private final StringProperty displayed = new SimpleStringProperty("");

    private ChatList chatList;

    @FXML
    private AnchorPane placeHolder;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ListView<Chat> chatListView;

    @FXML
    private Text resultDisplay;

    public ChatPanel(AnchorPane anchorPane, ChatList chatList) {
        super(FXML);
        this.chatList = chatList;
        resultDisplay.textProperty().bind(displayed);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(placeHolder, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(resultDisplay, 0.0, 0.0, 0.0, 0.0);
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
        chatList.add(new Chat(event.message, Sender.SURU));
        chatListView.scrollTo(chatList.size() - 1);
    }


    class ChatListViewCell extends ListCell<Chat> {

        @Override
        protected void updateItem(Chat chat, boolean empty) {
            super.updateItem(chat, empty);
            if (empty || chat == null) {
                setGraphic(null);
                setText(null);
            } else {
                switch(chat.getSender()) {
                case SURU:
                    setGraphic(new SuruChatMessage(chat).getRoot());
                    break;
                case USER:
                    setGraphic(new UserChatMessage(chat).getRoot());
                    break;
                }
            }
        }
    }
}
