package seedu.tache.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.events.ui.DetailedTaskCountChangedEvent;
import seedu.tache.commons.events.ui.FloatingTaskCountChangedEvent;
import seedu.tache.commons.util.FxViewUtil;

/**
 * Count of detailed and floating tasks.
 */
public class TaskCount extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(TaskCount.class);
    private static final String FXML = "TaskCount.fxml";

    @FXML
    private TextField detailedTaskCount;
    @FXML
    private TextField floatingTaskCount;

    public TaskCount(AnchorPane taskCountPlaceholder, String numDetailedTasks, String numFloatingTasks) {
        super(FXML);
        setDetailedTaskCount(numDetailedTasks);
        setFloatingTaskCount(numFloatingTasks);
        addToPlaceholder(taskCountPlaceholder);
        setEventHandlerForDetailedTaskCountChangeEvent();
        setEventHandlerForFloatingTaskCountChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolder) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(getRoot());
    }

    private void setDetailedTaskCount(String numDetailedTasks) {
        this.detailedTaskCount.setText(numDetailedTasks);
    }

    private void setFloatingTaskCount(String numFloatingTasks) {
        this.floatingTaskCount.setText(numFloatingTasks);
    }

    private void setEventHandlerForDetailedTaskCountChangeEvent() {
        detailedTaskCount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Detailed task count changed to : '" + newValue + "'");
                raise(new DetailedTaskCountChangedEvent(newValue));
            }
        });
    }

    private void setEventHandlerForFloatingTaskCountChangeEvent() {
        detailedTaskCount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Floating task count changed to : '" + newValue + "'");
                raise(new FloatingTaskCountChangedEvent(newValue));
            }
        });
    }
}
