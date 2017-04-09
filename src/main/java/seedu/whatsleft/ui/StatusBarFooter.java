package seedu.whatsleft.ui;

import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.whatsleft.commons.core.LogsCenter;
import seedu.whatsleft.commons.events.model.ConfigChangedEvent;
import seedu.whatsleft.commons.events.model.ShowStatusChangedEvent;
import seedu.whatsleft.commons.events.model.WhatsLeftChangedEvent;
import seedu.whatsleft.commons.util.FxViewUtil;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;
    @FXML
    private StatusBar displayStatus;

    private static final String FXML = "StatusBarFooter.fxml";

    public StatusBarFooter(AnchorPane placeHolder, String saveLocation) {
        super(FXML);
        addToPlaceholder(placeHolder);
        setSyncStatus("Not updated yet in this session");
        setSaveLocation(saveLocation);
        setDisplayStatus("Currently showing [PENDING] activities");
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
    //@@author A0121668A
    private void setDisplayStatus(String displayStatus) {
        this.displayStatus.setText(displayStatus);
    }
    //@@author

    @Subscribe
    public void handleWhatsLeftChangedEvent(WhatsLeftChangedEvent abce) {
        String lastUpdated = (new Date()).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus("Last Updated: " + lastUpdated);
    }
    //@@author A0121668A
    @Subscribe
    public void handleConfigChangedEvent(ConfigChangedEvent cce) {
        String newLocation = cce.data.getWhatsLeftFilePath();
        logger.info(LogsCenter.getEventHandlingLogMessage(cce, "Setting save location to " + newLocation));
        setSaveLocation(newLocation);
    }

    @Subscribe
    public void handleShowStatusChangedEvent(ShowStatusChangedEvent ssce) {
        String showStatus = ssce.toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(ssce, ""));
        setDisplayStatus(showStatus);
    }
}
