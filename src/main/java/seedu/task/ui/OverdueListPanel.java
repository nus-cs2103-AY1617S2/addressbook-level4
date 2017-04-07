package seedu.task.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.model.task.ReadOnlyTask;

//@@author A0139161J
/**
 * Panel containing list of tasks overdue
 */
public class OverdueListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(OverdueListPanel.class);
    private static final String FXML = "OverdueList.fxml";

    @FXML
    private ListView<ReadOnlyTask> overdueListView;

    public OverdueListPanel(AnchorPane overdueListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setConnections(taskList);
        addToPlaceholder(overdueListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        overdueListView.setItems(taskList);
        overdueListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane overdueListPlaceholder) {
        SplitPane.setResizableWithParent(overdueListPlaceholder, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        overdueListPlaceholder.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        overdueListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            overdueListView.scrollTo(index);
            overdueListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }
}

