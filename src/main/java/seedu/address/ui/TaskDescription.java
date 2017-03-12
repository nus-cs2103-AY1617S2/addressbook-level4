package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

/**
 * The Browser Panel of the App.
 */
public class TaskDescription extends UiPart<Region> {

    private static final String FXML = "TaskDescription.fxml";

    @FXML
    private TextArea content;

    @FXML
    private Label label;

    /**
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public TaskDescription(AnchorPane placeholder) {
        super(FXML);
        label = new Label("Task Description");
        //FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        //FxViewUtil.applyAnchorBoundaryParameters(content, 0.0, 0.0, 0.0, 0.0);
        content.setPrefHeight(600.0);
        content.setPrefWidth(600.0);

        placeholder.getChildren().add(content);
        placeholder.getChildren().add(label);
    }

    public void loadPersonPage(ReadOnlyTask person) {
        //content = new TextArea(person.getAsText());
        /*Label label = new Label("Task Description");
        HBox hb = new HBox();
        hb.getChildren().addAll(label, content);
        hb.setSpacing(10);*/
    }

}

