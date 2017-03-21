package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyActivity;

public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label place;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyActivity person, int displayedIndex) {
        super(FXML);
        description.setText(person.getDescription().description);
        id.setText(displayedIndex + ". ");
        phone.setText(person.getPriority().value);
        place.setText(person.getLocation().value);
        initTags(person);
    }

    private void initTags(ReadOnlyActivity person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
