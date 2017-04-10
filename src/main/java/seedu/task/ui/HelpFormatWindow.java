package seedu.task.ui;

import java.net.URL;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.task.MainApp;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.util.FxViewUtil;
//@@author A0142939W
/**
 * Controller for a help page
 */
public class HelpFormatWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpFormatWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpFormatWindow.fxml";
    private static final String TITLE = "Summary";
    private static final String USERGUIDE_URL = "/view/KITHelpSummary.html";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public HelpFormatWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(false);
        dialogStage.setMinWidth(835);
        FxViewUtil.setStageIcon(dialogStage, ICON);

        URL help = MainApp.class.getResource(USERGUIDE_URL);
        browser.getEngine().load(help.toString());
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
    }

    public void show() {
        logger.fine("Showing help summary page about the application.");
        dialogStage.showAndWait();
    }
}
