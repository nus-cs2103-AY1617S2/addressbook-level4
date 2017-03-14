// remember to change seedu to werkbook
// browser panel from viewing webpages to task previews

package seedu.address.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.util.FxViewUtil;
import seedu.task.model.task.ReadOnlyTask;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    @FXML
    private AnchorPane browser;
    @FXML
    private Label name1;
    @FXML
    private Label id;
    @FXML
    private Label tags;
    @FXML
    private Label description1;
    @FXML
    private Label start_time;
    @FXML
    private Label end_time;
    
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
    
    public void PersonCard(ReadOnlyTask task, int displayedIndex) {

        name1.setText(task.getName().taskName);
        id.setText(displayedIndex + ". ");
        description1.setText(task.getDescription().toString());
        start_time.setText(task.getStartDateTime().toString());
        end_time.setText(task.getEndDateTime().toString());
        tags.setText(task.getTags().toString());
    }

}
