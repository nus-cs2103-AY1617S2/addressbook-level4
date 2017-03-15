package seedu.doit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.doit.model.item.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label description;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.name.setText(task.getName().fullName);
        this.id.setText(displayedIndex + ". ");
        this.priority.setText(task.getPriority().value);
        this.description.setText(task.getDescription().value);
        initTags(task);

        if (task.hasStartTime()) {
            this.deadline.setText(task.getStartTime().value + " - " + task.getEndTime().value);
        } else if (task.hasEndTime()) {
            this.deadline.setText(task.getEndTime().value);
        } else {
            this.deadline.setText("");
        }
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> this.tags.getChildren().add(new Label(tag.tagName)));
    }
}
