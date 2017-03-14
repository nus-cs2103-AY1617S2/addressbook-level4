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
    private Label venue;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label urgencyLevel;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        title.setText(task.getTitle().title);
        id.setText(displayedIndex + ". ");
        venue.setText("Venue at: " + task.getVenue().value);
        startTime.setText("Start from: " + task.getStartTime().value);
        endTime.setText("End at: " + task.getEndTime().value);
        urgencyLevel.setText("Urgency: " + task.getUrgencyLevel().value);
        description.setText("Description: " + task.getDescription().value);
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
