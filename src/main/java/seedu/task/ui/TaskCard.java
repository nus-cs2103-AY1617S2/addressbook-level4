package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label due;
    @FXML
    private Label duration_start;
    @FXML
    private Label duration_end;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getDescription().description);
        id.setText(Integer.toString(displayedIndex));
        updateDueLabel(task);
        updateDurationLabel(task);
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void updateDueLabel(ReadOnlyTask task) {
        if (task.getDueDate() == null) {
            due.setManaged(false);
        } else {
            due.setManaged(true);
            due.setText(task.getDueDate().toString());
        }
    }

    private void updateDurationLabel(ReadOnlyTask task) {
        if (task.getDuration() == null) {
            duration_start.setManaged(false);
            duration_end.setManaged(false);
        } else {
            duration_start.setManaged(true);
            duration_end.setManaged(true);
            duration_start.setText(task.getDurationStart());
            duration_end.setText(task.getDurationEnd());
        }
    }
}
