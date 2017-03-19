package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyEvent;
import seedu.address.model.task.ReadOnlyTask;

public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

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

    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        title.setText(event.getTitle().title);
        id.setText(displayedIndex + ". ");
        venue.setText("Venue at: " + event.getVenue().toString());
        startTime.setText("Start from: " + event.getStartTime().toString());
        endTime.setText("End at: " + event.getEndTime().toString());
        urgencyLevel.setText("Urgency: " + event.getUrgencyLevel().toString());
        description.setText("Description: " + event.getDescription().toString());
        initTags(event);
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

    private void initTags(ReadOnlyEvent event) {
        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
