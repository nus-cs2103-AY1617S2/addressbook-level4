package seedu.taskit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskit.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private FlowPane tags;

    // @@author A0163996J
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        title.setText(task.getTitle().title);
        start.setText(task.getStart().toString());
        end.setText(task.getEnd().toString());
        if (task.getPriority().toString().equals("high")) {
            priority.setStyle("-fx-background-color: #fcafa6;");
            priority.setText(task.getPriority().toString());

        }
        else if (task.getPriority().toString().equals("medium")) {
            priority.setStyle("-fx-background-color: #fafc85;");
            priority.setText(task.getPriority().toString());
        }
        else {
            priority.setText("");
        }

        if (start.getText().length() > 0) {
        	start.setText("Start Time: " + start.getText());
        	end.setText("End Time: " + end.getText());
        }
        else if (end.getText().length() > 0) {
        	end.setText("Deadline: " + end.getText());
        }
        
        if (task.isDone()){
            getRoot().getStyleClass().add("done");
        }
        
        id.setText(displayedIndex + ". ");
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
