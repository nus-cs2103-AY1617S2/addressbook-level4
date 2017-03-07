package seedu.address.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.util.FxViewUtil;
<<<<<<< HEAD
import seedu.address.model.task.ReadOnlyTask;
=======
import seedu.address.model.person.ReadOnlyTask;
>>>>>>> parent of 9b5fb6b... test

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    @FXML
    private WebView browser;

    /**
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public BrowserPanel(AnchorPane placeholder) {
        super(FXML);
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the
                                                     // loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(browser);
    }

<<<<<<< HEAD
    public void loadTaskPage(ReadOnlyTask task) {
        loadPage("https://www.google.com.sg/#safe=off&q=" + task.getName().fullName.replaceAll(" ", "+"));
=======
    public void loadPersonPage(ReadOnlyTask person) {
        loadPage("https://www.google.com.sg/#safe=off&q=" + person.getName().fullName.replaceAll(" ", "+"));
>>>>>>> parent of 9b5fb6b... test
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
