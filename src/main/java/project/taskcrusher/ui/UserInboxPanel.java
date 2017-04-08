package project.taskcrusher.ui;

import java.util.Date;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import project.taskcrusher.commons.core.LogsCenter;
import project.taskcrusher.commons.events.model.ListsToShowUpdatedEvent;
import project.taskcrusher.commons.events.model.TimerToUpdateEvent;
import project.taskcrusher.commons.util.FxViewUtil;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.task.ReadOnlyTask;

//@@author A0127737X
/**
 * Panel containing the eventList and taskList.
 */
public class UserInboxPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(UserInboxPanel.class);
    private static final String FXML = "UserInboxPanel.fxml";
    private static final boolean SET_LIST_VISIBLE = true;
    private static final boolean SET_LIST_HIDDEN = false;
    private static final String MESSAGE_NO_ITEM_TO_SHOW = "Nothing to show!";
    private Date timer;

    @FXML
    private ListView<ReadOnlyTask> taskListView;
    @FXML
    private Label taskHeader;
    @FXML
    private ListView<ReadOnlyEvent> eventListView;
    @FXML
    private Label eventHeader;

    public UserInboxPanel(AnchorPane userInboxPlaceholder, ObservableList<ReadOnlyTask> taskList,
            ObservableList<ReadOnlyEvent> eventList) {
        super(FXML);
        timer = new Date();
        setConnections(taskList, eventList);
        addToPlaceholder(userInboxPlaceholder);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList, ObservableList<ReadOnlyEvent> eventList) {
        taskListView.setItems(taskList);
        eventListView.setItems(eventList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        eventListView.setCellFactory(listView -> new EventListViewCell());
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskListCard(task, getIndex() + 1, task.isOverdue(timer)).getRoot());
            }
        }
    }

    class EventListViewCell extends ListCell<ReadOnlyEvent> {

        @Override
        protected void updateItem(ReadOnlyEvent event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventListCard(event, getIndex() + 1, event.isOverdue(timer)).getRoot());
            }
        }
    }

    @Subscribe
    public void handleListsToShowUpdatedEvent(ListsToShowUpdatedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.eventCount == 0) {
            eventListView.setVisible(SET_LIST_HIDDEN);
            eventHeader.setText("Events: " + MESSAGE_NO_ITEM_TO_SHOW);
        } else {
            eventListView.setVisible(SET_LIST_VISIBLE);
            eventHeader.setText("Events: " + event.eventCount + " listed");
        }

        if (event.taskCount == 0) {
            taskListView.setVisible(SET_LIST_HIDDEN);
            taskHeader.setText("Tasks: " + MESSAGE_NO_ITEM_TO_SHOW);
        } else {
            taskListView.setVisible(SET_LIST_VISIBLE);
            taskHeader.setText("Tasks: " + event.taskCount + " listed");
        }
    }

    @Subscribe
    public void handleTimerToUpdateEvent(TimerToUpdateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        logger.info("Updated timer now at " + timer.toString());
        this.timer = new Date();
    }

}
