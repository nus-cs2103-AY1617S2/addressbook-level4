package seedu.whatsleft.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.whatsleft.commons.core.LogsCenter;
import seedu.whatsleft.commons.events.ui.EventPanelSelectionChangedEvent;
import seedu.whatsleft.commons.util.FxViewUtil;
import seedu.whatsleft.model.activity.ReadOnlyEvent;

//@@author A0148038A
/**
 * Panel containing the list of activities.
 */
public class EventListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);
    private static final String FXML = "EventListPanel.fxml";

    @FXML
    private ListView<ReadOnlyEvent> eventListView;

    public EventListPanel(AnchorPane eventListPlaceholder, ObservableList<ReadOnlyEvent> eventList) {
        super(FXML);
        setConnections(eventList);
        addToPlaceholder(eventListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyEvent> eventList) {
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new EventPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class EventListViewCell extends ListCell<ReadOnlyEvent> {

        @Override
        protected void updateItem(ReadOnlyEvent event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else if (event.getStartDate().value != null) {
                setGraphic(new EventCard(event, getIndex() + 1).getRoot());
            }
        }
    }

}
