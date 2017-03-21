package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyActivity;


public class DeadlineCard extends UiPart<Region> {

    private static final String FXML = "DeadlineListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label byDate;
    @FXML
    private Label byTime;
    @FXML
    private Label locations;
    @FXML
    private FlowPane tags;

    public DeadlineCard(ReadOnlyActivity deadline, int displayedIndex) {
        super(FXML);
        description.setText(deadline.getDescription().description);
        id.setText(displayedIndex + ". ");
        byDate.setText("By Date: " + deadline.getByDate().value);
        byTime.setText("By Time: " + deadline.getEndTime().value);
        locations.setText(deadline.getLocation().value);
        initTags(deadline);
    }

    private void initTags(ReadOnlyActivity deadline) {
        deadline.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
