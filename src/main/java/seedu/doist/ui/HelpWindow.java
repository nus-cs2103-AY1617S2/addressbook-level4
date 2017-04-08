package seedu.doist.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.util.FxViewUtil;

//@@author A0140887W
/**
 * Controller for a window that loads the cheatsheet image
 */
public class HelpWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final double MAX_WIDTH = 1040;
    private static final double START_HEIGHT = 800;

    @FXML
    private ImageView helpImage;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane helpWindowRoot;

    private final Stage dialogStage;

    public HelpWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setHeight(START_HEIGHT);
        dialogStage.setWidth(MAX_WIDTH);
        dialogStage.setMaxWidth(MAX_WIDTH);
        FxViewUtil.setStageIcon(dialogStage, ICON);
        FxViewUtil.applyAnchorBoundaryParameters(scrollPane, 0.0, 0.0, 0.0, 0.0);
    }

    public void show() {
        logger.fine("Showing help page about the application.");
        dialogStage.showAndWait();
    }
}
