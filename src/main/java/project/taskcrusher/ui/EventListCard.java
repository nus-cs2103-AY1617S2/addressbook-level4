package project.taskcrusher.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.shared.DateUtilApache;

//@@author A0127737X
/**
 * Controller for EventListCard.fxml. Reads a ReadOnlyEvent and create the layout accordingly.
 */
public class EventListCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";
    private static final String LOCATION_AT = "@ ";
//    private static final String OVERDUE_STYLE_CLASS = "overdue";

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
    private Label priority;
    @FXML
    private FlowPane timeslots;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView tickIcon;
    @FXML
    private ImageView overdueIcon;

    public EventListCard(ReadOnlyEvent event, int displayedIndex, boolean isOverdue) {
        super(FXML);
        name.setText(event.getName().name);
        name.setMinWidth(Region.USE_PREF_SIZE);
        id.setText(displayedIndex + ". ");
        showLocation(event);
        showDescription(event);
        showPriority(event);
        showEventTimeSlots(event);
        displayComplete(event);
        displayOverdueStatusIfApplicable(event.isComplete(), isOverdue);

        initTags(event);
    }

    private void displayComplete(ReadOnlyEvent event) {
        if (!event.isComplete()) {
            tickIcon.setVisible(false);
        }
    }

    private void displayOverdueStatusIfApplicable(boolean isComplete, boolean isOverdue) {
        if (!isComplete && isOverdue) {
            overdueIcon.setVisible(true);
            overdueIcon.setManaged(true);
            for (Node child: timeslots.getChildren()) {
                child.setStyle("-fx-text-fill: red"); //should not be done this way
            }
        } else {
            overdueIcon.setVisible(false);
            overdueIcon.setManaged(false);
        }
    }

    private void showDescription(ReadOnlyEvent event) {
        if (event.getDescription().hasDescription()) {
            description.setText(event.getDescription().toString());
        } else {
            description.setVisible(false);
        }
        description.setMinWidth(Region.USE_PREF_SIZE);
    }

    private void showPriority(ReadOnlyEvent event) {
        priority.setText("p=" + event.getPriority().priority);
        switch (event.getPriority().priority) {
        case "1":
            priority.getStyleClass().add("priority-one");
            break;
        case "2":
            priority.getStyleClass().add("priority-two");
            break;
        case "3":
            priority.getStyleClass().add("priority-three");
            break;
        default:
            priority.setVisible(false);
        }
        priority.setMinWidth(Region.USE_PREF_SIZE);
    }

    private void showLocation(ReadOnlyEvent event) {
        if (event.getLocation().hasLocation()) {
            eventLocation.setText(LOCATION_AT + event.getLocation().location);
        } else {
            eventLocation.setText("");
        }
        eventLocation.setMinWidth(Region.USE_PREF_SIZE);
    }

    private void showEventTimeSlots(ReadOnlyEvent event) {
        event.getTimeslots().forEach(timeslot -> timeslots.getChildren().add(new Label(
                DateUtilApache.timeslotAsStringForUi(timeslot))));
    }

    private void initTags(ReadOnlyEvent event) {
        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
