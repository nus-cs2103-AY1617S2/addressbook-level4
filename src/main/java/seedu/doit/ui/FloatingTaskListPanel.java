package seedu.doit.ui;

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
import seedu.doit.commons.events.ui.FloatingTaskPanelSelectionChangedEvent;
import seedu.doit.commons.util.FxViewUtil;
import seedu.doit.model.item.ReadOnlyFloatingTask;

/**
 * Panel containing the list of tasks.
 */
public class FloatingTaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FloatingTaskListPanel.class);
    @FXML
    private ListView<ReadOnlyFloatingTask> taskListView;

    public FloatingTaskListPanel(AnchorPane placeholder, ObservableList<ReadOnlyFloatingTask> floatingTaskList) {
        super(FXML);
        setConnections(floatingTaskList);
        addToPlaceholder(placeholder);
    }

    private void setConnections(ObservableList<ReadOnlyFloatingTask> floatingTaskList) {
        this.taskListView.setItems(floatingTaskList);
        this.taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        this.taskListView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                    raise(new FloatingTaskPanelSelectionChangedEvent(newValue));
                }
            });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            this.taskListView.scrollTo(index);
            this.taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyFloatingTask> {

        @Override
        protected void updateItem(ReadOnlyFloatingTask floatingTask, boolean empty) {
            super.updateItem(floatingTask, empty);

            if (empty || floatingTask == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FloatingTaskCard(floatingTask, getIndex() + 1).getRoot());
            }
        }
    }

}
