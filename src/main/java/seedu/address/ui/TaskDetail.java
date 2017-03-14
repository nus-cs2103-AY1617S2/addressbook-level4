package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.FxViewUtil;
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

    /**
     * @param placeholder The AnchorPane where the TaskDetail must be inserted
     */
    public TaskDetail(AnchorPane placeholder) {
        super(FXML);

        /*taskName = new TextField("empty");
        startTime = new TextField();
        endTime = new TextField();
        tags = new TextField();*/
        taskName.setText("input");
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().addAll(getRoot());
    }

    public void loadPersonPage(ReadOnlyTask task) {
        taskName.setText(task.getContent().toString());
        //startTime.setText(task.getDateTime().toString());
        //tags.setText(task.getTags().toString());
    }

}
