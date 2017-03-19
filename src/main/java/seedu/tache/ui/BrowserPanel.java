package seedu.tache.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.tache.commons.util.FxViewUtil;
import seedu.tache.model.task.ReadOnlyTask;

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
        loadCalendar();
    }

    public void loadTaskPage(ReadOnlyTask task) {
        loadPage("https://www.google.com.sg/#safe=off&q=" + task.getName().fullName.replaceAll(" ", "+"));
    }

    public void loadPage(String url) {
        browser.getEngine().load(url);
    }

    private void loadCalendar() {
        try {
            String calendarTemplate = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")
                    + "/src/main/resources/html/calendar.html")));
            browser.getEngine().loadContent(calendarTemplate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add_event_today(String name) {
        browser.getEngine().executeScript("add_event('" + name + "')");
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}
