package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyActivity;

public class ActivityCard extends UiPart<Region> {

    private static final String FXML = "ActivityListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label locations;
    @FXML
    private FlowPane tags;

    public ActivityCard(ReadOnlyActivity activity, int displayedIndex) {
        super(FXML);
        description.setText(activity.getDescription().description);
        id.setText(displayedIndex + ". ");
        priority.setText(activity.getPriority().value);
        locations.setText(activity.getLocation().value);
        initTags(activity);
    }

    private void initTags(ReadOnlyActivity activity) {
        activity.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
