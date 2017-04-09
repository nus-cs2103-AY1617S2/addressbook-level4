package seedu.doit.ui;
//@@author: A0160076L
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.doit.commons.util.FxViewUtil;
import seedu.doit.model.item.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    private static ObservableList<ReadOnlyTask> mainTaskList;
    @FXML
    private ListView<ReadOnlyTask> eventListView;

    public EventListPanel(AnchorPane eventListPlaceholder, ObservableList<ReadOnlyTask> eventList) {
        super(FXML);
        setConnections(eventList);
        addToPlaceholder(eventListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyTask> eventList) {
        mainTaskList = eventList;
        this.eventListView.setItems(eventList.filtered(task -> task.hasStartTime()
                                   ));
        this.eventListView.setCellFactory(listView -> new EventListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        this.eventListView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                    raise(new TaskPanelSelectionChangedEvent(newValue));
                }
            });
    }

    protected void clearSelection() {
        this.eventListView.getSelectionModel().clearSelection();
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            this.eventListView.scrollTo(index);
            this.eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class EventListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(event, mainTaskList.indexOf(event) + 1).getRoot());
            }
        }
    }

}
