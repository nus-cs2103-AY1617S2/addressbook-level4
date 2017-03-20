package project.taskcrusher.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import project.taskcrusher.model.event.ReadOnlyEvent;

public class EventListCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";
    private static final String MESSAGE_NO_LOCATION = "No location specified";
    private static final String MESSAGE_DEADLINE_BY = "By ";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label location;
    @FXML
    private Label description;
    @FXML
    private FlowPane timeSlots;
    @FXML
    private FlowPane tags;

    public EventListCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        name.setText(event.getEventName().toString());
        id.setText(displayedIndex + ". ");
        showLocation(event);
        showDescription(event);

        initTags(event);
    }

    private void showDescription(ReadOnlyEvent event) {
        description.setText(event.getDescription().toString());
    }

    private void showLocation(ReadOnlyEvent event) {
        if (event.getLocation().hasLocation()) {
            location.setText(event.getLocation().location);
        } else {
            location.setText(MESSAGE_NO_LOCATION);
        }
    }

    private void showEventTimeSlots(ReadOnlyEvent event) {
    }

    private void initTags(ReadOnlyEvent event) {
        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
