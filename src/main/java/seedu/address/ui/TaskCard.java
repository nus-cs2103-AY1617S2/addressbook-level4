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
        initEndTime(task);
        initVenue(task);
        initDescription(task);
        initTags(task);
    }
    //@@author A0122017Y
    private void initVenue(ReadOnlyTask task){
        boolean isVenuePresent = task.getVenue().isPresent();
        String venueContent = "";
        if (isVenuePresent) {
            venueContent = "Venue at: " + task.getVenue().toString();
        }
        venue.setText(venueContent);
    }
    
    private void initDescription(ReadOnlyTask task){
        String descriptionContent = "";
        if (task.getDescription().isPresent()) {
            descriptionContent = "Description: " + task.getDescription().toString();
        }
        description.setText(descriptionContent);
    }
    
    private void initUrgency(ReadOnlyTask task){
        String urgencyContent = "";
        if (task.getUrgencyLevel().isPresent()) {
            urgencyContent = "Urgency: " + task.getDescription().toString();
        }
        urgencyLevel.setText(urgencyContent);
    }
    
    private void initEndTime(ReadOnlyTask task){
        String endTimeContent = "";
        if (task.getEndTime().isPresent()) {
            endTimeContent = "Deadline: " + task.getEndTime().toString();
        }
        description.setText(endTimeContent);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
    //@@
}
