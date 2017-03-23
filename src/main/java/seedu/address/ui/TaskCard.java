package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label start;
    @FXML
    private Label deadline;
    @FXML
    private Label priority;
    @FXML
    private FlowPane tags;
    @FXML
    private Label notes;
    @FXML
    private Label completion;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        start.setText("Start: " + task.getStart().value);
        deadline.setText("Deadline: " + task.getDeadline().value);

        if (task.getPriority().value == 0) {
            priority.setText("Priority: ");
        } else {
            priority.setText("Priority: " + task.getPriority().value);
        }
        initTags(task);

        notes.setText("Notes: " + task.getNotes().value);
        completion.setText("Completion: " + task.getCompletion().value);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
