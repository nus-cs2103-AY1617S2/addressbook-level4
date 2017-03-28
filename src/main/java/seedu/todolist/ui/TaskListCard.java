package seedu.todolist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.todolist.model.task.Task;

public class TaskListCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;
    @FXML
    private Label complete;
    @FXML
    private ImageView imageView;


    public TaskListCard(Task task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        startTime.setText(task.getStartTime() != null ? task.getStartTime().toString() : "");
        endTime.setText(task.getEndTime() != null ? task.getEndTime().toString() : "");
        description.setText(task.getDescription() == null ? "" : task.getDescription());
        Image incompleteIcon = new Image("/images/incomplete-icon.png");
        Image completeIcon = new Image("/images/complete-icon.png");
        if (!task.isComplete()) {
            imageView.setImage(incompleteIcon);
        } else {
            imageView.setImage(completeIcon);
        }
        initTags(task);
    }

    private void initTags(Task task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    //private void initStartTime(ReadOnlyTask task) {
    //  startTime.getChildren().add(new Label(task.getStartTime().toString()));
    //}

}
