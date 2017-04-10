package seedu.task.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import seedu.task.commons.util.FxViewUtil;

/**
 * Controller for a help page
 */
public class HelpWindow extends Window {

    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final String NOTICE = "press ENTER to exit";
    //@@author A0163848R
    private static final String HELP_HTML =
            "https://cs2103jan2017-f11-b3.github.io/main/";
    //@@author

    @FXML
    private WebView browser;

    public HelpWindow() {
        super(FXML);

        setTitle(TITLE + " - " + NOTICE);
        stage.setMaximized(true); //TODO: set a more appropriate initial size
        FxViewUtil.setStageIcon(stage, ICON);

        browser.getEngine().load(HELP_HTML);

        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);

        setAccelerators();
    }

    private void setAccelerators() {
        browser.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    handleExit();
                    keyEvent.consume();
                }
            }
        });
    }

    @FXML
    public void handleExit() {
        getStage().close();
    }
}
