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
import seedu.geekeep.commons.core.TaskCategory;
import seedu.geekeep.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.geekeep.commons.util.FxViewUtil;
import seedu.geekeep.model.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    //Corresponding tab indices in the TabPane for different tabs.
    private static final int ALL_TAB = 0;
    private static final int UPCOMING_TAB = 1;
    private static final int FINISHED_TAB = 2;

    private ListView<ReadOnlyTask> currentListView;

    protected String type;
    protected Logger logger = null;

    @FXML
    private TabPane tabPanePlaceHolder;

    @FXML
    private ListView<ReadOnlyTask> allListView;

    @FXML
    private ListView<ReadOnlyTask> upcomingListView;

    @FXML
    private ListView<ReadOnlyTask> finishedListView;

    public TaskListPanel(String fxml, AnchorPane taskListPlaceholder,
            ObservableList<ReadOnlyTask> filteredList) {
        super(fxml);
        currentListView = allListView;
        setConnections(filteredList, allListView);
        setConnections(filteredList, finishedListView);
        setConnections(filteredList, upcomingListView);
        addToPlaceholder(taskListPlaceholder);
        selectTab(ALL_TAB);
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

    public void clearSelection() {
        currentListView.getSelectionModel().clearSelection();
    }

    public void switchListView(TaskCategory category) {
        switch (category) {
        case ALL:
            selectTab(ALL_TAB);
            currentListView = allListView;
            break;
        case FINISHED:
            selectTab(FINISHED_TAB);
            currentListView = finishedListView;
            break;
        default:
            assert category == TaskCategory.UPCOMING;
            selectTab(UPCOMING_TAB);
            currentListView = upcomingListView;
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
