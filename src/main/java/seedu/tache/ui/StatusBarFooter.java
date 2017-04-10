package seedu.tache.ui;

import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.events.model.TaskManagerChangedEvent;
import seedu.tache.commons.events.storage.DataFileLocationChangedEvent;
import seedu.tache.commons.util.FxViewUtil;

/**
 * A UI for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;

    private static final String FXML = "StatusBarFooter.fxml";

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

    //@@author A0142255M
    public String getSaveLocation() {
        return this.saveLocationStatus.getText();
    }

    public String getSyncStatus() {
        return this.syncStatus.getText();
    }
    //@@author

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce) {
        String lastUpdated = (new Date()).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus("Last Updated: " + lastUpdated);
    }

    //@@author A0142255M
    /**
     * Updates the save location status bar when the data file location is changed.
     *
     * @param event    Event which contains the new data file path.
     */
    @Subscribe
    public void handleDataFileLocationChangedEvent(DataFileLocationChangedEvent event) {
        assert event != null;
        String newLocation = event.toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Setting new data file location to" + newLocation));
        setSaveLocation("New Location: " + newLocation);
    }
}
