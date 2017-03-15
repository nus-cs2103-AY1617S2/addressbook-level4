package werkbook.task.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import werkbook.task.commons.util.FxViewUtil;
import werkbook.task.model.task.ReadOnlyTask;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    @FXML
    private AnchorPane browser;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
    @FXML
    private Label description;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;

    /**
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
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

    void loadTaskPage(ReadOnlyTask task) {

        name.setText(task.getName().taskName);
        // id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().toString());
        startDateTime.setText(task.getStartDateTime().toString());
        endDateTime.setText(task.getEndDateTime().toString());
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        tags.getChildren().clear();
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
