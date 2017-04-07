package typetask.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import typetask.commons.core.LogsCenter;
import typetask.commons.util.FxViewUtil;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final String COMMAND_SUMMARY_IMAGE = "/images/commandShortcut.png";

    //@@author A0139154E
    @FXML
    private ImageView commandSummary;

    private Image commandSummaryImage = new Image(COMMAND_SUMMARY_IMAGE);
    //@@author
    
    private final Stage dialogStage;

    public HelpWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        //@@author A0139154E
        dialogStage.setMaximized(false);
        dialogStage.setMaxHeight(920);
        dialogStage.setMaxWidth(1050);
        dialogStage.setMinHeight(920);
        dialogStage.setMinWidth(1050);
        //@@author
        FxViewUtil.setStageIcon(dialogStage, ICON);

        commandSummary.setImage(commandSummaryImage);

    }

    public void show() {
        logger.fine("Showing help page about the application.");
        dialogStage.showAndWait();
    }
}
