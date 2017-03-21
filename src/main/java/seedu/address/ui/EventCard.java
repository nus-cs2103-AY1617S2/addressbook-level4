package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.FromDate;
import seedu.address.model.person.Location;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.person.StartTime;
import seedu.address.model.person.ToDate;

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

    public EventCard(ReadOnlyActivity event, int displayedIndex) {
        super(FXML);
        description.setText(event.getDescription().description);
        id.setText(displayedIndex + ". "); 
        fromDate.setText("Start Date: " + event.getFromDate().value);
        
        if(event.getToDate().value==null){
            toDate.setText("End Date: -");
        } else {
        	toDate.setText("End Date: " + event.getToDate().value);
        }
        
        startTime.setText("Start Time: " + event.getStartTime().value);
        
        if(event.getEndTime().value==null){
            endTime.setText("End Time: -");
        } else {
        	endTime.setText("End Time: " + event.getEndTime().value);
        }
        
        locations.setText(event.getLocation().value);
        initTags(event);
    }

    private void initTags(ReadOnlyActivity event) {
        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
