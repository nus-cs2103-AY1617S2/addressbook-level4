package seedu.taskit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import seedu.taskit.model.tag.Tag;
import seedu.taskit.model.tag.TagColor;
import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private Rectangle overdue;
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
    private Circle priority_shape;
    @FXML
    private Label priority;
    @FXML
    private FlowPane tags;

    // @@author A0141011J
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        title.setText(task.getTitle().title);
        start.setText(task.getStart().toString());
        end.setText(task.getEnd().toString());

        //Set the lable for priority
        if (task.getPriority().toString().equals("high")) {
            priority_shape.setStroke(Color.RED);
            priority.setText("H");
            priority.setTextFill(Color.RED);
        }
        else if (task.getPriority().toString().equals("medium")) {
            priority_shape.setStroke(Color.ORANGE);
            priority.setText("M");
            priority.setTextFill(Color.ORANGE);
        }
        else if (task.getPriority().toString().equals("low")){
            priority_shape.setStroke(Color.LIGHTGREEN);
            priority.setText("L");
            priority.setTextFill(Color.LIGHTGREEN);
        }

        //display start and end time for events and deadlines
        if (start.getText().length() > 0) {
            start.setText("From " + start.getText());
            end.setText("To " + end.getText());
        }
        else if (end.getText().length() > 0) {
            end.setText("Due " + end.getText());
        }

        if (task.isDone()){
            overdue.setFill(Color.FORESTGREEN);
        }

        if (task.isOverdue()) {
            overdue.setFill(Color.BROWN);
        }

        id.setText(displayedIndex + ". ");
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        UniqueTagList tagList = task.getTags();
        for(Tag t : tagList) {
            Label newTag = new Label(t.tagName);
            String tagColor = TagColor.getColorCode(t.getColor());
            newTag.setStyle("-fx-background-color: " + tagColor + ";");
            tags.getChildren().add(newTag);
        }
    }
}
