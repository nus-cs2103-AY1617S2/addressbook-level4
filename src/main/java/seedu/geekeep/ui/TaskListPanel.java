//@@author A0148037E
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
    private static final String EVENT_FXML = "EventListPanel.fxml";
    private static final String FLOATING_TASK_FXML = "FloatingTaskListPanel.fxml";
    private static final String DEADLINE_FXML = "DeadlineListPanel.fxml";

    //Corresponding tab indices in the TabPane for different tabs.
    private static final int ALL_TAB = 0;
    private static final int UPCOMING_TAB = 1;
    private static final int FINISHED_TAB = 2;

    private ListView<ReadOnlyTask> currentListView;
    private String type;

    @FXML
    private TabPane tabPanePlaceHolder;

    @FXML
    private ListView<ReadOnlyTask> allListView;

    @FXML
    private ListView<ReadOnlyTask> upcomingListView;

    @FXML
    private ListView<ReadOnlyTask> finishedListView;

    public TaskListPanel(String type, AnchorPane taskListPlaceholder,
            ObservableList<ReadOnlyTask> filteredList) {
        super(getFxmlFromType(type));
        this.type = type;
        currentListView = allListView;
        setConnections(filteredList, allListView);
        setConnections(filteredList, finishedListView);
        setConnections(filteredList, upcomingListView);
        addToPlaceholder(taskListPlaceholder);
        selectTab(UPCOMING_TAB);
    }

    private static String getFxmlFromType(String type) {
        if ("deadline".equals(type)) {
            return DEADLINE_FXML;
        } else if ("floatingTask".equals(type)) {
            return FLOATING_TASK_FXML;
        } else {
            assert "event".equals(type);
            return EVENT_FXML;
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
        case ALL:
            selectTab(ALL_TAB);
            break;
        case DONE:
            selectTab(FINISHED_TAB);
            break;
        default:
            assert category == TaskCategory.UNDONE;
            selectTab(UPCOMING_TAB);
            break;
        }
        logger.info("Switched to " + category + " in " + type);
    }

    public void selectTab(int tab) {
        tabPanePlaceHolder.getTabs().get(tab).setDisable(false);
        tabPanePlaceHolder.getSelectionModel().select(tab);
        for (int i = 0; i < 3; i++) {
            if (i != tab) {
                tabPanePlaceHolder.getTabs().get(i).setDisable(true);
            }
        }
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
