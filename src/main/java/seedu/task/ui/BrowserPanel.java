package seedu.task.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.task.commons.util.FxViewUtil;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";
    protected static final String FXML_LIGHT = "BrowserPanel.fxml";
    protected static final String FXML_DARK = "BrowserPanelDark.fxml";

    @FXML
    private WebView browser;

  //@@author A0142487Y-reused
    /**
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public BrowserPanel(AnchorPane placeholder, String...fxml) {
        super(fxml.length == 0 ? FXML : fxml[0]);
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the
                                                     // loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(browser);
        loadPage();
    }

//    public void loadTaskPage(ReadOnlyTask task) {
//        loadPage("https://www.google.com.sg/maps/place/" + task.getLocation().value.replaceAll(" ", "+"));
//    }

    //@@author A0140063X
    /**
     * Loads Google Calendar.
     */
    public void loadPage() {
        browser.getEngine().load("https://calendar.google.com/calendar/render#main_7%7Cmonth");
    }

    //@@author
    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}
