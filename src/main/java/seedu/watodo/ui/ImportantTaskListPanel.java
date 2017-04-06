package seedu.watodo.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.watodo.commons.core.LogsCenter;
import seedu.watodo.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.watodo.commons.util.FxViewUtil;
import seedu.watodo.model.task.ReadOnlyTask;

/**
 * Panel containing the list of persons.
 */
public class ImportantTaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(ImportantTaskListPanel.class);
    private static final String FXML = "ImportantTaskListPanel.fxml";

    @FXML
    private ListView<ReadOnlyTask> importantTaskListView;

    public ImportantTaskListPanel(AnchorPane importantTaskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setConnections(taskList);
        addToPlaceholder(importantTaskListPlaceholder);
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        importantTaskListView.setItems(taskList);
        importantTaskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        importantTaskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in important task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            importantTaskListView.scrollTo(index);
            importantTaskListView.getSelectionModel().clearAndSelect(index);
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
