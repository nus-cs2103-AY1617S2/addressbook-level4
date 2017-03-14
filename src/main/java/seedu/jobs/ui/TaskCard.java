package seedu.jobs.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.jobs.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label endTime;
    @FXML
    private Label startTime;
    @FXML
    private Label description;
//    @FXML
//    private Label email;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        startTime.setText(task.getStartTime().value);
        endTime.setText(task.getEndTime().value);
        description.setText(task.getDescription().value);
        //initTags(task);
    }

//    private void initTags(ReadOnlyTask task) {
//        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
//    }
}
