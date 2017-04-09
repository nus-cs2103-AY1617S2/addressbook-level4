//@@author A0142255M
package seedu.tache.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.events.ui.FilteredTaskListUpdatedEvent;
import seedu.tache.commons.util.FxViewUtil;
import seedu.tache.logic.Logic;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * Count of timed and floating tasks.
 */
public class TaskCount extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(TaskCount.class);
    private static final String FXML = "TaskCount.fxml";

    @FXML
    private Label timedTaskCount;
    @FXML
    private Label floatingTaskCount;

    public TaskCount(AnchorPane taskCountPlaceholder, Logic logic) {
        super(FXML);
        setTimedTaskCount(logic.getFilteredTaskList());
        setFloatingTaskCount(logic.getFilteredTaskList());
        addToPlaceholder(taskCountPlaceholder);
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder(AnchorPane placeHolder) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(getRoot());
    }

    private void setTimedTaskCount(ObservableList<ReadOnlyTask> taskList) {
        assert taskList != null;
        String count = getTimedTaskCount(taskList);
        this.timedTaskCount.setText(count);
    }

    /**
     * Returns a String representing the number of timed tasks.
     *
     * @param taskList    Filtered task list displayed at task list panel.
     * @return    No. of timed tasks.
     */
    private String getTimedTaskCount(ObservableList<ReadOnlyTask> taskList) {
        assert taskList != null;
        int count = 0;
        for (ReadOnlyTask task : taskList) {
            if (task.getTimedStatus() == true) {
                count++;
            }
        }
        return Integer.toString(count);
    }

    private void setFloatingTaskCount(ObservableList<ReadOnlyTask> taskList) {
        assert taskList != null;
        String count = getFloatingTaskCount(taskList);
        this.floatingTaskCount.setText(count);
    }

    /**
     * Returns a String representing the number of floating tasks.
     *
     * @param taskList    Filtered task list displayed at task list panel.
     * @return    No. of floating tasks.
     */
    private String getFloatingTaskCount(ObservableList<ReadOnlyTask> taskList) {
        assert taskList != null;
        int count = 0;
        for (ReadOnlyTask task : taskList) {
            if (task.getTimedStatus() == false) {
                count++;
            }
        }
        return Integer.toString(count);
    }

    /**
     * Recalculates and sets the counts of timed and floating tasks.
     *
     * @param event    FilteredTaskListUpdatedEvent which contains updated task list.
     */
    @Subscribe
    public void handleFilteredTaskListUpdatedEvent(FilteredTaskListUpdatedEvent event) {
        ObservableList<ReadOnlyTask> taskList = event.getFilteredTaskList();
        String oldTimedCount = this.timedTaskCount.getText();
        String newTimedCount = getTimedTaskCount(taskList);
        if (oldTimedCount != newTimedCount) {
            this.timedTaskCount.setText(newTimedCount);
            logger.fine("Timed task list count changed to : '" + newTimedCount + "'");
        }
        String oldFloatingCount = this.floatingTaskCount.getText();
        String newFloatingCount = getFloatingTaskCount(taskList);
        if (oldFloatingCount != newFloatingCount) {
            this.floatingTaskCount.setText(newFloatingCount);
            logger.fine("Floating task list count changed to : '" + newFloatingCount + "'");
        }
    }

}
