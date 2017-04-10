package org.teamstbf.yats.ui;

import java.util.logging.Logger;

import org.teamstbf.yats.commons.core.LogsCenter;
import org.teamstbf.yats.commons.util.FxViewUtil;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final String USERGUIDE_URL = "https://github.com/"
            + "CS2103JAN2017-T16-B4/main/blob/master/docs/UserGuide.md";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public HelpWindow() {
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
        logger.fine("Showing help page about the application.");
        dialogStage.showAndWait();
    }
}
