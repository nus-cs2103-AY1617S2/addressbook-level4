package seedu.geekeep.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.commons.core.TaskCategory;
import seedu.geekeep.commons.events.ui.PersonPanelSelectionChangedEvent;
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


    //TODO only works for v0.2 checks
    public TaskListPanel(String type, AnchorPane taskListPlaceholder) {
        super(getFxmlFromType(type));
        addToPlaceholder(taskListPlaceholder);
    }

    //TODO  only works for v0.2 checks
    public TaskListPanel(String type, AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> allList) {
        super(getFxmlFromType(type));
        this.type = type;
        currentListView = allListView;
        setConnections(allList, allListView);
        setConnections(allList.filtered(t -> t.isDone()), completedListView);
        setConnections(allList.filtered(t -> !t.isDone()), upcomingListView);
        addToPlaceholder(taskListPlaceholder);
    }

    //TODO this method should not be there. After v0.2 it is to remove
    private static String getFxmlFromType(String type) {
        if (type.equals("deadline")) {
            return DEADLINEFXML;
        } else if (type.equals("floatingTask")) {
            return FTASKFXML;
        } else {
            return EVENTFXML;
        }
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList, ListView<ReadOnlyTask> taskListView) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new PersonListViewCell());
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
                        raise(new PersonPanelSelectionChangedEvent(newValue));
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

/*  //TODO scrollTo should works for all the ListView
    public void scrollToVersion2() {

    }*/

    class PersonListViewCell extends ListCell<ReadOnlyTask> {

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
