package seedu.tache.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.events.ui.TaskListTypeChangedEvent;
import seedu.tache.commons.util.FxViewUtil;

/**
 * Panel containing: type of tasks shown in task list, no. of detailed tasks and no. of floating tasks.
 */
public class TaskListType extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskListType.class);
    private static final String FXML = "TaskListType.fxml";

    @FXML
    private TextField taskListType;

    public TaskListType(AnchorPane taskListTypePlaceholder, String typeOfTasks) {
        super(FXML);
        setConnections(typeOfTasks);
        addToPlaceholder(taskListTypePlaceholder);
    }

    private void setConnections(String typeOfTasks) {
        taskListType.setText(typeOfTasks);
        setEventHandlerForTaskListTypeChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolder) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(getRoot());
    }

    private void setEventHandlerForTaskListTypeChangeEvent() {
        taskListType.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Task list type changed to : '" + newValue + "'");
                raise(new TaskListTypeChangedEvent(newValue));
            }
        });
    }

}
