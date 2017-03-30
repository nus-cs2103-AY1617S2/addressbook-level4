package seedu.task.ui;

import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;
import org.ocpsoft.prettytime.PrettyTime;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.model.FilePathChangedEvent;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.util.FxViewUtil;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;

    private static final String FXML = "StatusBarFooterDefault.fxml";
    protected static final String FXML_Light = "StatusBarFooterLight.fxml";
    protected static final String FXML_Dark= "StatusBarFooterDark.fxml";

    public StatusBarFooter(AnchorPane placeHolder, String saveLocation) {
        super(FXML);
        addToPlaceholder(placeHolder);
        setSyncStatus("Not updated yet in this session");
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
    }
  //@@author A0142487Y-reused
    public StatusBarFooter(AnchorPane placeHolder, String saveLocation, String fxml) {
        super(fxml);
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
    public void handleFilePathChangedEvent(FilePathChangedEvent fpce) {
        setSaveLocation("./" + fpce.path);
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce) {
        PrettyTime pretty = new PrettyTime();
        Date now = new Date();
        String lastUpdated = (new Date()).toString() + ", " + pretty.format(now);
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus("Last Updated: " + lastUpdated);
    }
}
