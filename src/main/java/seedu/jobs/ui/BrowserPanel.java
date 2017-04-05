package seedu.jobs.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.jobs.commons.util.FxViewUtil;
import seedu.jobs.model.task.ReadOnlyTask;

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
        
        String username = "esdaesa";
        browser.getEngine().load("https://calendar.google.com/calendar/render#main_7%7Cmonth");
        browser.getEngine().getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    
                    @Override
                    public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                        if (newValue == State.SUCCEEDED) {
                            browser.getEngine().executeScript(""
                                    + "document.getElementById('Email').value = \"" + username + "\";"
                                    + "document.getElementById('next').click();");
                        }
                    }
                });
    }

    public void loadTaskPage() {
    	//loadPage("https://www.google.com.sg/#safe=off&q=" + task.getName().fullName.replaceAll(" ", "+"));
    	loadPage("https://calendar.google.com/calendar/render#main_7%7Cmonth");
    }

    public void loadPage(String url) {
        String password = "asdasd";
        browser.getEngine().executeScript("document.getElementById('Passwd').value = \"" + password + "\";"
                                    + "document.getElementById('signIn').click();");
        
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}
