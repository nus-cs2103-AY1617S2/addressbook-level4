package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Panel containing a group of tasks.
 */
public class TaskGroupPanel extends UiPart<Region> {

    private final Logger logger = LogsCenter.getLogger(TaskGroupPanel.class);
    private static final String FXML = "TaskGroupPanel.fxml";

    @FXML
    private TitledPane titledPane;

    @FXML
    private ListView<ReadOnlyTask> taskGroupView;

    public TaskGroupPanel(String title, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setTitle(title);
        setConnections(taskList);
        closeTitlePane();
        updateListViewHeight();
    }

    void updateListViewHeight() {
        final int countItems = getTasks().size();
        final int ITEM_HEIGHT = 110;
        // Add 2 pixels for border
        taskGroupView.setPrefHeight(countItems * ITEM_HEIGHT + 2);
    }

    public void closeTitlePane() {
        titledPane.setExpanded(false);
    }

    public void openTitlePane() {
        titledPane.setExpanded(true);
    }

    public void setTitle(String title) {
        titledPane.setText(title);
    }

    public String getTitle() {
        return titledPane.getText();
    }

    public ObservableList<ReadOnlyTask> getTasks() {
        return taskGroupView.getItems();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskGroupView.setItems(taskList);
        taskGroupView.setCellFactory(listView -> new TaskGroupViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskGroupView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task group panel " + getTitle() + " changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskGroupView.scrollTo(index);
            taskGroupView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskGroupViewCell extends ListCell<ReadOnlyTask> {

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
