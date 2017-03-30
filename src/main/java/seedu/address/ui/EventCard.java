package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyEvent;

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
//    @FXML
//    private Label startTime;
//    @FXML
//    private Label startDate;
//    @FXML
//    private Label endTime;
//    @FXML
//    private Label endDate;
    @FXML
    private Label locations;
    @FXML
    private FlowPane tags;

    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        description.setText(event.getDescription().description);
        id.setText(displayedIndex + ". ");

        duration.setText(event.getStartTime().toString()
                + " " + event.getStartDate().toString()
                + " ~ " + event.getEndTime().toString()
                + " " + event.getEndDate().toString());

        locations.setText("@" + event.getLocation().toString());
        initTags(event);
    }

    private void initTags(ReadOnlyEvent event) {
        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
