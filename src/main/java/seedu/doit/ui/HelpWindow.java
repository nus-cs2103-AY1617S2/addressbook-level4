package seedu.doit.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.util.FxViewUtil;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Region> {

	private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
	private static final String ICON = "/images/help_icon.png";
	private static final String FXML = "HelpWindow.fxml";
	private static final String TITLE = "Help";
	private static final String USERGUIDE_URL =
			"https://github.com/CS2103JAN2017-W14-B3/main/blob/master/docs/UserGuide.md";
	private final Stage dialogStage;
	@FXML
	private WebView browser;

	public HelpWindow() {
		super(FXML);
		Scene scene = new Scene(getRoot());
		//Null passed as the parent stage to make it non-modal.
		this.dialogStage = createDialogStage(TITLE, null, scene);
		this.dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
		FxViewUtil.setStageIcon(this.dialogStage, ICON);
		this.browser.getEngine().load(USERGUIDE_URL);
		FxViewUtil.applyAnchorBoundaryParameters(this.browser, 0.0, 0.0, 0.0, 0.0);
	}

	public void show() {
		logger.fine("Showing help page about the application.");
		this.dialogStage.showAndWait();
	}
}
