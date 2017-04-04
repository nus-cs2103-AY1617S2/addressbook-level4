package seedu.task.ui;

import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.task.commons.core.FileNameHandler;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.storage.LoadDataEvent;
import seedu.task.commons.events.ui.ChangePathNameEvent;
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

	private static final String FXML = "StatusBarFooter.fxml";

	public StatusBarFooter(AnchorPane placeHolder, String saveLocation) {
		super(FXML);
		addToPlaceholder(placeHolder);
		setSyncStatus("Not updated yet in this session");
		setSaveLocation("./" + FileNameHandler.getFileName());
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
		String lastUpdated = (new Date()).toString();
		logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
		setSyncStatus("Last Updated: " + lastUpdated);
	}

	// @@author A0163845X
	@Subscribe
	private void handleChangePathNameEvent(ChangePathNameEvent event) {
		logger.info(LogsCenter.getEventHandlingLogMessage(event));
		setSaveLocation("./" + event.getPathName());
	}

	// @@author A0163845X
	@Subscribe
	private void loadDataEvent(LoadDataEvent event) {
		logger.info(LogsCenter.getEventHandlingLogMessage(event));
		setSaveLocation("./" + event.getPathName());
	}
}
