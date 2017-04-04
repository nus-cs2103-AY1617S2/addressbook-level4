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
    protected static final String FXML_LIGHT = "StatusBarFooterLight.fxml";
    protected static final String FXML_DARK = "StatusBarFooterDark.fxml";

  //@@author A0142487Y-reused
    public StatusBarFooter(AnchorPane placeHolder, String saveLocation, String...fxml) {
        super(fxml.length==0?FXML:fxml[0]);
        addToPlaceholder(placeHolder);
        setSyncStatus("Not updated yet in this session");
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
    }

    //@@author
    private void addToPlaceholder(AnchorPane placeHolder) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(getRoot());
    }

    //@@author A0142939W
    private void setSaveLocation(String location) {
        this.saveLocationStatus.setText(location);
    }

    private void setSyncStatus(String status) {
        this.syncStatus.setText(status);
    }

    //@@author A0142939W
    @Subscribe
    public void handleFilePathChangedEvent(FilePathChangedEvent fpce) {
        setSaveLocation("./" + fpce.path);
    }

    //@@author A0140063X
    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce) {
        PrettyTime pretty = new PrettyTime();
        Date now = new Date();
        String lastUpdated = (new Date()).toString() + ", " + pretty.format(now);
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus("Last Updated: " + lastUpdated);
    }
}
