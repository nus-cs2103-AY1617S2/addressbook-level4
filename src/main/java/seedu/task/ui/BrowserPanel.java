package seedu.task.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.model.task.ReadOnlyTask;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    @FXML
    private WebView browser;

    /**
     * @param placeholder
     *            The AnchorPane where the BrowserPanel must be inserted
     */
    public BrowserPanel(AnchorPane placeholder) {
	super(FXML);
	placeholder.setOnKeyPressed(Event::consume); // To prevent triggering
	// events for typing inside
	// the
	// loaded Web page.
	FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
	placeholder.getChildren().add(browser);
    }

    public void loadTaskPage(ReadOnlyTask task) {
	loadPage("https://www.google.com.sg/#safe=off&q=" + task.getTaskName().fullTaskName.replaceAll(" ", "+"));
    }

    public void loadPage(String url) {
	browser.getEngine().load(url);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
	browser = null;
    }

}
