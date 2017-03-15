package seedu.address.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.ReadOnlyTask;

/**
 * The Calender Panel of the App.
 */
//tutorial
//https://www.youtube.com/watch?v=HiZ-glk9_LE&t=568s
public class CalenderPanel extends UiPart<Region> {
	private static final String FXML = "CalenderPanel.fxml";
    @FXML
    private HBox test;
    
    public CalenderPanel(AnchorPane calendertPlaceholder) {
        super(FXML);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        calendertPlaceholder.getChildren().add(getRoot());
    }
}
