package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyTask;

//@@author A0148038A
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label byTimeDate;
    @FXML
    private Label locations;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        description.setText(task.getDescription().description);
        id.setText(displayedIndex + ". ");
        
        if (task.getPriority().value != null) {
        	priority.setText("Priority: " + task.getPriority().value.toUpperCase());
        }
        
        if (task.getByTime().value != null) {
        	byTimeDate.setText("BY " + task.getByTime().value + " " + task.getByDate().value);
        } else {
            byTimeDate.setText("BY " + task.getByDate().value);
        }

        if (task.getLocation().value != null) {
        	locations.setText("@" + task.getLocation().value);
        } else {
        	locations.setText(" ");
        }
        
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
