package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String EMPTY_STRING = "";

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
    @FXML
    private Label complete;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getDescription().description);
        id.setText(Integer.toString(displayedIndex));
        updateDueLabel(task);
        updateDurationLabel(task);
        updateCompleteLabel(task);
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    // @@joshuaching A0163673Y
    private void updateDueLabel(ReadOnlyTask task) {
        if (task.getDueDate() == null) {
            due.setVisible(false);
        } else {
            due.setVisible(true);
            due.setText(task.getDueDate().toString());
        }
    }

    private void updateDurationLabel(ReadOnlyTask task) {
        if (task.getDuration() == null) {
            duration_start.setVisible(false);
            duration_end.setVisible(false);
        } else {
            duration_start.setVisible(true);
            duration_end.setVisible(true);
            duration_start.setText(task.getDurationStart());
            duration_end.setText(task.getDurationEnd());
        }
    }

    private void updateCompleteLabel(ReadOnlyTask task) {
        if (task.getComplete() == null) {
            return;
        }
        complete.setText(task.getComplete().getCompletion() ? "✓" : EMPTY_STRING);
    }
    // @@author
}
