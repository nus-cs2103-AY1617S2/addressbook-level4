package seedu.taskmanager.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.util.FxViewUtil;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "User Guide";
    private static final String USERGUIDE_URL =
            "https://github.com/CS2103JAN2017-F12-B3/main/blob/master/docs/UserGuide.md";

    @FXML
    private WebView browser;

    private final Stage dialogStage;
   
    public HelpWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        FxViewUtil.setStageIcon(dialogStage, ICON);

        browser.getEngine().load(USERGUIDE_URL);
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
    }
    
    public void show() {
        logger.fine("Showing help page about the application.");
        dialogStage.showAndWait();
    }
}