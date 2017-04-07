//@@author A0141138N
package seedu.onetwodo.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.commons.util.FxViewUtil;

public class HelpUGWindow extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final String USERGUIDE_URL = "https://cs2103jan2017-f14-b1.github.io/main/UserGuide.html";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public HelpUGWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        // Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); // TODO: set a more appropriate initial
        // size
        FxViewUtil.setStageIcon(dialogStage, ICON);

        browser.getEngine().load(USERGUIDE_URL);
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
    }

    public void show() {
        logger.fine("Showing user guide about the application.");
        dialogStage.showAndWait();
    }
}
