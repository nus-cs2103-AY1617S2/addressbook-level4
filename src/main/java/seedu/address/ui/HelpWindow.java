package seedu.address.ui;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

/**
 * Controller for a help page
 */
public class HelpWindow extends Window {

    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final String HELP_HTML_FILE = "/offdoc.html?mdfile=docs/UserGuide.md";

    @FXML
    private WebView browser;

    public HelpWindow() {
        super(FXML);
        
        setTitle(TITLE);
        stage.setMaximized(true); //TODO: set a more appropriate initial size
        FxViewUtil.setStageIcon(stage, ICON);

        browser.getEngine().load("file://" + System.getProperty("user.dir") + HELP_HTML_FILE);
			
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
    }

}
