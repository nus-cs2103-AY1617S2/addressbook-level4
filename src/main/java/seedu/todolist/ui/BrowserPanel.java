package seedu.todolist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.todolist.commons.util.FxViewUtil;
import seedu.todolist.model.task.Task;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    @FXML
    private AnchorPane display;

    @FXML
    private Label nameLabel;

    @FXML
    private Label tagsheader;

    @FXML
    private FlowPane tagsFlow;

    @FXML
    private VBox taskDetails;




    //@@author A0144240W
    /**
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public BrowserPanel(AnchorPane placeholder) {
        super(FXML);
        FxViewUtil.applyAnchorBoundaryParameters(display, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(display);
    }

    //@@author A0144240W
    public void loadPersonPage(Task task) {
        tagsFlow.setHgap(10);
        nameLabel.setText(task.getName().toString());
        tagsheader.setText("Tags:");
        task.getTags().forEach(tag -> tagsFlow.getChildren().add(new Label(tag.tagName)));
    }

    //@@author A0144240W
    public void freeResources() {
        tagsFlow.getChildren().clear();
    }


}
