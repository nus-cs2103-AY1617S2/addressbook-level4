package seedu.tache.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tache.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label startDate;

    @FXML
    private Label endDate;

    @FXML
    private Label startTime;

    @FXML
    private Label endTime;

    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        id.setText(Integer.toString(displayedIndex) + ". ");
        name.setText(task.getName().toString());
        startDate.setText(task.getStartDate().toString());
        endDate.setText(task.getEndDate().toString());
        startTime.setText(task.getStartTime().toString());
        endTime.setText(task.getEndTime().toString());
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
