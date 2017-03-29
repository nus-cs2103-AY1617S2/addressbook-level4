package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label instruction;
    @FXML
    private Label priority;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane recurringTag;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        title.setText(task.getTitle().title);
        id.setText(displayedIndex + ". ");
        date.setText(task.getDeadline().toString());
        instruction.setText(task.getInstruction().value);
        priority.setText(task.getPriority().toString());
        initTags(task);
        
        if(task.getDeadline().isRecurring()) {
            initRecurTag();
        }
    }

    private void initRecurTag() {
        //recurringTag.getChildren().add(new Label("Recurring"));
        recurringTag.setStyle("-fx-background-color: #ff884c; -fx-blend-mode: multipy;");
        //recurringTag.setStyle("-fx-background-blend-mode: multiply;");
        
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
