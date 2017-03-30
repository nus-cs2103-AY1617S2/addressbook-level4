package org.teamstbf.yats.ui;

import org.teamstbf.yats.commons.util.FxViewUtil;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

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

    public void loadPersonPage(ReadOnlyEvent person) {
	loadPage("https://www.google.com.sg/#safe=off&q=" + person.getTitle().fullName.replaceAll(" ", "+"));
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
