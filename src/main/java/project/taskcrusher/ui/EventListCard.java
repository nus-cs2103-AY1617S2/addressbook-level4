package project.taskcrusher.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import project.taskcrusher.commons.util.UiDisplayUtil;
import project.taskcrusher.model.event.ReadOnlyEvent;

//@@author A0127737X
/**
 * Controller for EventListCard.fxml. Reads a ReadOnlyEvent and create the layout accordingly.
 */
public class EventListCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

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
        showIdAndName(event, displayedIndex);
        showLocation(event);
        showDescription(event);
        showPriority(event);
        showEventTimeSlots(event);
        displayCompleteStatusIfApplicable(event);
        displayOverdueStatusIfApplicable(event, isOverdue);

        initTags(event);
    }

    private void showIdAndName(ReadOnlyEvent event, int displayedIndex) {
        name.setText(event.getName().name);
        name.setMinWidth(Region.USE_PREF_SIZE);
        id.setText(displayedIndex + ". ");
    }

    private void displayCompleteStatusIfApplicable(ReadOnlyEvent event) {
        if (!event.isComplete()) {
            tickIcon.setVisible(false);
        }
    }

    private void displayOverdueStatusIfApplicable(ReadOnlyEvent event, boolean isOverdue) {
        if (!event.isComplete() && isOverdue) {
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
        description.setText(event.getDescription().description);
        if (event.getDescription().hasDescription()) {
            description.setMinWidth(Region.USE_PREF_SIZE);
        } else {
            description.setVisible(false);
        }
    }

    private void showPriority(ReadOnlyEvent event) {
        priority.setText(UiDisplayUtil.priorityForUi(event.getPriority()));
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
        eventLocation.setText(UiDisplayUtil.locationForUi(event.getLocation()));
        eventLocation.setMinWidth(Region.USE_PREF_SIZE);
    }

    private void showEventTimeSlots(ReadOnlyEvent event) {
        event.getTimeslots().forEach(timeslot -> timeslots.getChildren().add(new Label(
                UiDisplayUtil.timeslotAsStringForUi(timeslot))));
    }

    private void initTags(ReadOnlyEvent event) {
        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
