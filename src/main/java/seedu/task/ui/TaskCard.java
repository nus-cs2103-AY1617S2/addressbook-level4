package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.model.task.ReadOnlyTask;

//@@author A0163673Y
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String EMPTY_STRING = "";
    private static final String CHECKMARK_STRING = "✓";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label due;
    @FXML
    private Label durationStart;
    @FXML
    private Label durationEnd;
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
            durationStart.setVisible(false);
            durationEnd.setVisible(false);
        } else {
            durationStart.setVisible(true);
            durationEnd.setVisible(true);
            durationStart.setText(task.getDurationStart());
            durationEnd.setText(task.getDurationEnd());
        }
    }

    private void updateCompleteLabel(ReadOnlyTask task) {
        if (task.getComplete() == null) {
            return;
        }
        complete.setText(task.getComplete().getCompletion() ? CHECKMARK_STRING : EMPTY_STRING);
    }
}
