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
import seedu.jobs.model.LoginInfo;

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
    public BrowserPanel(AnchorPane placeholder, LoginInfo loginInfo) {
        super(FXML);
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the
                                                     // loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(browser);

        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();
        ChangeListener<State> emailListener = new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                if (newValue == State.SUCCEEDED) {
                    browser.getEngine().getLoadWorker().stateProperty().removeListener(this);
                    browser.getEngine().executeScript("function fillInPassword() {document.getElementById('Passwd')"
                            + ".value = \"" + password + "\";" + "document.getElementById('signIn').click();}");
                    browser.getEngine().executeScript("document.getElementById('Email').value = \"" + email + "\";");
                    browser.getEngine().executeScript("document.getElementById('next').click();");
                    browser.getEngine().executeScript("setTimeout(fillInPassword, 500)");
                }
            }
        };
        browser.getEngine().getLoadWorker().stateProperty().addListener(emailListener);
        browser.getEngine().load("https://calendar.google.com/calendar/render#main_7%7Cmonth");

    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}
