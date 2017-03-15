package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.ReadOnlyTask;

/**
 * The Task Description of the App.
 */
public class TaskDescription extends UiPart<Region> {

    private static final String FXML = "TaskDescription.fxml";

    @FXML
    private TextArea content;

    /**
     * @param placeholder The AnchorPane where the TaskDescription must be inserted
     */
    public TaskDescription(AnchorPane placeholder) {
        super(FXML);
        FxViewUtil.applyAnchorBoundaryParameters(content, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().addAll(content);
    }

    public void loadPersonPage(ReadOnlyTask person) {
        content.setText(person.getAsText());
    }

}

