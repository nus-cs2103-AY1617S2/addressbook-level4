package seedu.doit.ui;

import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.events.model.TaskManagerChangedEvent;
import seedu.doit.commons.events.storage.TaskManagerLoadChangedEvent;
import seedu.doit.commons.events.storage.TaskManagerSaveChangedEvent;
import seedu.doit.commons.util.FxViewUtil;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);
    private static final String FXML = "StatusBarFooter.fxml";
    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;

    public StatusBarFooter(AnchorPane placeHolder, String saveLocation) {
        super(FXML);
        addToPlaceholder(placeHolder);
        setSyncStatus("Not updated yet in this session");
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder(AnchorPane placeHolder) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(getRoot());
    }

    private void setSaveLocation(String location) {
        this.saveLocationStatus.setText(location);
    }

    private void setSyncStatus(String status) {
        this.syncStatus.setText(status);
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce) {
        String lastUpdated = new Date().toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus("Last Updated: " + lastUpdated);
    }

    @Subscribe
    public void handleTaskManagerSaveChangedEvent(TaskManagerSaveChangedEvent event) {
        String lastUpdated = new Date().toString();
        String filePath = event.getFilePath();
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Setting last updated status to " + lastUpdated));
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Setting save location to " + filePath));
        setSyncStatus("Last Updated: " + lastUpdated);
        setSaveLocation(filePath);
    }

    @Subscribe
    public void handleTaskManagerLoadChangedEvent(TaskManagerLoadChangedEvent event) {
        String lastUpdated = new Date().toString();
        String filePath = event.getFilePath();
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Setting last updated status to " + lastUpdated));
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Setting save location to " + filePath));
        setSyncStatus("Last Updated: " + lastUpdated);
        setSaveLocation(filePath);
    }
}
