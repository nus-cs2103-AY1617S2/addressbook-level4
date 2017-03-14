package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Displays the task details and content.
 */
public class TaskDetail extends UiPart<Region> {

    private static final String FXML = "TaskDetail.fxml";

    @FXML
    private TextField taskName;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private TextField tags;

    @FXML
    private Label labelTaskName;

    /**
     * @param placeholder The AnchorPane where the TaskDetail must be inserted
     */
    public TaskDetail(AnchorPane placeholder) {
        super(FXML);

        labelTaskName.setTextFill(Color.CHOCOLATE);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().addAll(getRoot());
    }

    public void loadPersonPage(ReadOnlyTask task) {
        String taggings = "";
        taskName.setText(task.getContent().toString());
        startTime.setText(task.getDateTime().toString());
        for(Tag tag : task.getTags()) {
            taggings += tag.toString();
        }
        tags.setText(taggings);
    }
}
