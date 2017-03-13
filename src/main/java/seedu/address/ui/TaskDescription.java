package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

/**
 * The Task Description of the App.
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
        content = new TextArea();
        //FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        //FxViewUtil.applyAnchorBoundaryParameters(content, 0.0, 0.0, 0.0, 0.0);
        content.setPrefHeight(600.0);
        content.setPrefWidth(600.0);

        //content.setScaleY(0.1);

        placeholder.getChildren().addAll(content, label);
    }

    public void loadPersonPage(ReadOnlyTask person) {
        content.setText(person.getAsText());
        /*Label label = new Label("Task Description");
        HBox hb = new HBox();
        hb.getChildren().addAll(label, content);
        hb.setSpacing(10);*/
    }

}

