package seedu.whatsleft.ui;

import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.whatsleft.model.activity.ReadOnlyEvent;

//@@author A0148038A
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label duration;
    @FXML
    private Label locations;
    @FXML
    private FlowPane tags;

    //@@author A0124377A
    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        description.setText(event.getDescriptionToShow());
        id.setText(displayedIndex + ". ");

        duration.setText(event.getDurationToShow());

        if (event.getLocation() == null) {
            locations.setText("");
        } else {
            locations.setText(event.getLocationToShow());
        }
        initTags(event);
        setCardLook(event);
    }

    private void initTags(ReadOnlyEvent event) {
        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
    //@@author A0124377A
    /**
     * Function to add badge to list card for respective statuses
     * @param event
     */
    private void setCardLook(ReadOnlyEvent event) {
        if (event.isOver()) {
            cardPane.getStyleClass().add("status-complete");
        } else if (isDueToday(event)) {
            cardPane.getStyleClass().add("status-dueOrOverdue");
        }
    }

    /**
     * Determines if event is due at current day of local date time.
     * @param event
     */
    private boolean isDueToday(ReadOnlyEvent event) {
        LocalDateTime eventTime = LocalDateTime.of(event.getStartDate().getValue(),
                event.getStartTime().getValue());
        return eventTime.getDayOfYear() == LocalDateTime.now().getDayOfYear();
    }
}
