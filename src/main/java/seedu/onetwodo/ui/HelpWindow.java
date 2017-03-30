package seedu.onetwodo.ui;

import java.net.URL;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.onetwodo.MainApp;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.commons.util.FxViewUtil;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);

    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Cheatsheet";
    private static final String HELPWINDOW_URL = "/view/help.html";
    public static final String HELP_WINDOW_ROOT_FIELD_ID = "#helpWindowRoot";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    // @@author A0141138N
    public HelpWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        // Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(false); // TODO: set a more appropriate initial
        // size
        FxViewUtil.setStageIcon(dialogStage, ICON);

        URL help = MainApp.class.getResource(HELPWINDOW_URL);
        browser.getEngine().load(help.toString());
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
    }

    public void show() {
        logger.fine("Showing help page about the application.");
        dialogStage.showAndWait();
    }
}
