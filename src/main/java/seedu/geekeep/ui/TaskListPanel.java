package seedu.geekeep.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.commons.core.TaskCategory;
import seedu.geekeep.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.geekeep.commons.util.FxViewUtil;
import seedu.geekeep.model.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String EVENTFXML = "EventListPanel.fxml";
    private static final String FTASKFXML = "FloatingTaskListPanel.fxml";
    private static final String DEADLINEFXML = "DeadlineListPanel.fxml";
    private ListView<ReadOnlyTask> currentListView;
    private String type;

    @FXML
    private TabPane tabPanePlaceHolder;

    @FXML
    private ListView<ReadOnlyTask> allListView;

    @FXML
    private ListView<ReadOnlyTask> upcomingListView;

    @FXML
    private ListView<ReadOnlyTask> completedListView;

    public TaskListPanel(String type, AnchorPane taskListPlaceholder,
            ObservableList<ReadOnlyTask> allList) {
        super(getFxmlFromType(type));
        this.type = type;
        currentListView = allListView;
        setConnections(allList, allListView);
        setConnections(allList, completedListView);
        setConnections(allList, upcomingListView);
        addToPlaceholder(taskListPlaceholder);
    }

    //TODO to remove
    private static String getFxmlFromType(String type) {
        if ("deadline".equals(type)) {
            return DEADLINEFXML;
        } else if ("floatingTask".equals(type)) {
            return FTASKFXML;
        } else {
            assert "event".equals(type);
            return EVENTFXML;
        }
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList,
            ListView<ReadOnlyTask> taskListView) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent(taskListView);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent(ListView<ReadOnlyTask> taskListView) {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            currentListView.scrollTo(index);
            currentListView.getSelectionModel().clearAndSelect(index);
        });
    }


    public void switchListView(TaskCategory category) {
        switch (category) {
        case UNDONE:
            tabPanePlaceHolder.getSelectionModel().select(1);
            break;
        case DONE:
            tabPanePlaceHolder.getSelectionModel().select(2);
            break;
        default:
            tabPanePlaceHolder.getSelectionModel().select(0);
            break;
        }
        logger.info("Switched to " + category + " in " + type);
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        protected int getSourceIndex() {
            FilteredList<ReadOnlyTask> filteredList = (FilteredList<ReadOnlyTask>) getListView().getItems();
            return filteredList.getSourceIndex(getIndex());
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getSourceIndex() + 1).getRoot());
            }
        }
    }

}
