package project.taskcrusher.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.shared.DateUtilApache;

public class EventListCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";
    private static final String MESSAGE_NO_LOCATION = "No location";
    private static final String LOCATION_AT = "@ ";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label eventLocation; //named like this to avoid collision between the builtin name
    @FXML
    private Label description;
    @FXML
    private FlowPane timeslots;
    @FXML
    private FlowPane tags;

    public EventListCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        name.setText(event.getName().name);
        id.setText(displayedIndex + ". ");
        showLocation(event);
        showDescription(event);
        showEventTimeSlots(event);

        initTags(event);
    }

    private void showDescription(ReadOnlyEvent event) {
        description.setText(event.getDescription().toString());
    }

    private void showLocation(ReadOnlyEvent event) {
        if (event.getLocation().hasLocation()) {
            eventLocation.setText(LOCATION_AT + event.getLocation().location);
        } else {
            eventLocation.setText(MESSAGE_NO_LOCATION);
        }
    }

    private void showEventTimeSlots(ReadOnlyEvent event) {
        event.getTimeslots().forEach(timeslot -> timeslots.getChildren().add(new Label(
                DateUtilApache.timeslotAsStringForUi(timeslot))));
    }

    private void initTags(ReadOnlyEvent event) {
        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
