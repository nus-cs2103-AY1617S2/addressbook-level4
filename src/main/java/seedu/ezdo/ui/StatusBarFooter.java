package seedu.ezdo.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.ezdo.commons.core.LogsCenter;
import seedu.ezdo.commons.events.model.EzDoChangedEvent;
import seedu.ezdo.commons.events.storage.EzDoDirectoryChangedEvent;
import seedu.ezdo.commons.util.FxViewUtil;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final Date CURRENT_DATE = new Date();
    private static final String MESSAGE_SET_SAVE_LOCATION = "Setting save location to ";
    private static final String MESSAGE_LAST_UPDATED = "Last Updated: ";
    private static final String MESSAGE_SET_LAST_UPDATED = "Setting last updated status to ";
    private static final String STATUS_BAR_DATE_FORMAT = "dd/MM/YYYY H:mm:ss";
    private static final String MESSAGE_NOT_UPDATED = "Not updated yet in this session";
    private static final String MESSAGE_SAVE_LOCATION_TOOLTIP =
            "ezDo Directory Box\nDisplays the directory of ezDo data file.";
    private static final String MESSAGE_STATUS_BAR_TOOLTIP =
            "Status Box\nDisplays when ezDo data file is last updated.";

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;

    private static final String FXML = "StatusBarFooter.fxml";

    public StatusBarFooter(AnchorPane placeHolder, String saveLocation) {
        super(FXML);
        syncStatus.setTooltip(new Tooltip(MESSAGE_STATUS_BAR_TOOLTIP));
        saveLocationStatus.setTooltip(new Tooltip(MESSAGE_SAVE_LOCATION_TOOLTIP));
        addToPlaceholder(placeHolder);
        setSyncStatus(MESSAGE_NOT_UPDATED);
        setSaveLocation(saveLocation);
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
    public void handleEzDoChangedEvent(EzDoChangedEvent ezce) {
        SimpleDateFormat df = new SimpleDateFormat(STATUS_BAR_DATE_FORMAT);
        String lastUpdated = df.format(CURRENT_DATE);
        logger.info(LogsCenter.getEventHandlingLogMessage(ezce, MESSAGE_SET_LAST_UPDATED + lastUpdated));
        setSyncStatus(MESSAGE_LAST_UPDATED + lastUpdated);
    }
  //@@author A0139248X
    /**
     * Updates the status bar footer to show the new ezdo storage file path and the last updated time
     */
    @Subscribe
    public void handleEzDoDirectoryChangedEvent(EzDoDirectoryChangedEvent ezce) {
        String lastUpdated = CURRENT_DATE.toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(ezce, MESSAGE_SET_LAST_UPDATED + lastUpdated));
        logger.info(LogsCenter.getEventHandlingLogMessage(ezce, MESSAGE_SET_SAVE_LOCATION + ezce.getPath()));
        setSyncStatus(MESSAGE_LAST_UPDATED + lastUpdated);
        setSaveLocation(ezce.getPath());
    }
}
