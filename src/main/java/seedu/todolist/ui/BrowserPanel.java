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




    /**
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public BrowserPanel(AnchorPane placeholder) {
        super(FXML);
        //resultDisplay.textProperty().bind(displayed);
        //FxViewUtil.applyAnchorBoundaryParameters(resultDisplay, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(display, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(display);

       // resultDisplay.appendText("Task Display");
        //placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the
                                                     // loaded Web page.
       // FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        //placeholder.getChildren().add(browser);
    }

    public void loadPersonPage(Task task) {
        tagsFlow.setHgap(10);
        nameLabel.setText(task.getName().toString());
        tagsheader.setText("Tags:");
        //will need to fix this
        task.getTags().forEach(tag -> tagsFlow.getChildren().add(new Label(tag.tagName)));
    }

    public void freeResources() {
        tagsFlow.getChildren().clear();
    }


}
