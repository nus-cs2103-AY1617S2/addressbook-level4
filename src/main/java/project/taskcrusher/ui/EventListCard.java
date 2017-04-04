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

    public EventListCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        name.setText(event.getName().name);
        id.setText(displayedIndex + ". ");
        showLocation(event);
        showDescription(event);
        showPriority(event);
        showEventTimeSlots(event);
        displayComplete(event);
        displayOverdueStatusIfAny(event);

        initTags(event);
    }

    private void displayComplete(ReadOnlyEvent event) {
        if (!event.isComplete()) {
            tickIcon.setVisible(false);
        }
    }

    private void displayOverdueStatusIfAny(ReadOnlyEvent event) {
        if (event.isOverdue()) {
            overdueIcon.setVisible(true);
            overdueIcon.setManaged(true);
//            timeslots.getStyleClass().add(OVERDUE_STYLE_CLASS);
            for (Node child: timeslots.getChildren()) {
                child.setStyle("-fx-text-fill: red"); //should not be done this way
            }
        } else {
            overdueIcon.setVisible(false);
            overdueIcon.setManaged(false);
//            timeslots.getStyleClass().add(OVERDUE_STYLE_CLASS);
        }
    }

    private void showDescription(ReadOnlyEvent event) {
        if (event.getDescription().hasDescription()) {
            description.setText("//" + event.getDescription().toString());
        } else {
            description.setVisible(false);
        }
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
    }

    private void showLocation(ReadOnlyEvent event) {
        if (event.getLocation().hasLocation()) {
            eventLocation.setText(LOCATION_AT + event.getLocation().location);
        } else {
            eventLocation.setText("");
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
