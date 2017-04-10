//@@author A0142255M
package seedu.tache.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.events.ui.TaskListTypeChangedEvent;
import seedu.tache.commons.util.FxViewUtil;

/**
 * A UI containing the type of tasks shown in task list.
 */
public class TaskListType extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListType.class);
    private static final String FXML = "TaskListType.fxml";

    @FXML
    private Label taskListType;

    public TaskListType(AnchorPane taskListTypePlaceholder, String typeOfTasks) {
        super(FXML);
        taskListType.setText(typeOfTasks);
        addToPlaceholder(taskListTypePlaceholder);
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder(AnchorPane placeHolder) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(getRoot());
    }

    /**
     * Updates task list type.
     *
     * @param event    TaskListTypeChangedEvent which contains new task list type.
     */
    @Subscribe
    public void handleTaskListTypeChangedEvent(TaskListTypeChangedEvent event) {
        String oldTaskListType = this.taskListType.getText();
        String newTaskListType = event.getTaskListType();
        if (oldTaskListType != newTaskListType) {
            this.taskListType.setText(newTaskListType);
            logger.fine("Task list type changed to : '" + newTaskListType + "'");
        }
    }

}
