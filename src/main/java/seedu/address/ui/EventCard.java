package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyEvent;

public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label fromDate;
    @FXML
    private Label toDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label locations;
    @FXML
    private FlowPane tags;

    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        description.setText(event.getDescription().description);
        id.setText(displayedIndex + ". ");
        fromDate.setText("Start Date: " + event.getStartDate().value);

        if (event.getEndDate().value == null) {
            toDate.setText("End Date: -");
        } else {
            toDate.setText("End Date: " + event.getEndDate().value);
        }

        startTime.setText("Start Time: " + event.getStartTime().value);

        if (event.getEndTime().value == null) {
            endTime.setText("End Time: -");
        } else {
            endTime.setText("End Time: " + event.getEndTime().value);
        }

        locations.setText(event.getLocation().value);
        initTags(event);
    }

    private void initTags(ReadOnlyEvent event) {
        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
