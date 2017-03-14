package seedu.doit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.doit.model.item.ReadOnlyEvent;

public class EventCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label description;
    @FXML
    private Label startTime;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane tags;

    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        this.name.setText(event.getName().fullName);
        this.id.setText(displayedIndex + ". ");
        this.priority.setText(event.getPriority().value);
        this.description.setText(event.getDescription().value);
        this.startTime.setText(event.getStartTime().value);
        this.deadline.setText(event.getEndTime().value);
        initTags(event);
    }

    private void initTags(ReadOnlyEvent event) {
        event.getTags().forEach(tag -> this.tags.getChildren().add(new Label(tag.tagName)));
    }
}
