package seedu.task.ui;

import java.util.Date;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.storage.TargetFileRequestEvent;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.model.UserPrefs;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {
    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;

    private static final String FXML = "StatusBarFooter.fxml";

    public StatusBarFooter(AnchorPane placeHolder, String saveLocation, UserPrefs prefs) {
        super(FXML);
        addToPlaceholder(placeHolder);
        setSyncStatus("Not updated yet in this session");
        setSaveLocation(prefs.getGuiSettings().getLastLoadedYTomorrow());
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
    public void handleTaskChangedEvent(TaskManagerChangedEvent abce) {
        String lastUpdated = (new Date()).toString();
        LOGGER.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus("Last Updated: " + lastUpdated);
    }

    @Subscribe
    public void handleTargetFileRequestEvent(TargetFileRequestEvent tfre) {
        this.saveLocationStatus.setText(tfre.getTargetFile().toString());
    }
}
