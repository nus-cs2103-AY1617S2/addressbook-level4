package seedu.geekeep.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.geekeep.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask person, int displayedIndex) {
        super(FXML);
        name.setText(person.getTitle().fullTitle);
        id.setText("#" + displayedIndex + " ");

        if (person.getEndDateTime() != null && person.getStartDateTime() != null) {
            phone.setText(person.getStartDateTime() + " until " + person.getEndDateTime());
        } else if (person.getEndDateTime() != null && person.getStartDateTime() == null) {
            phone.setText(person.getEndDateTime().value);
        } else {
            phone.setText(null);
        }

        if (person.getLocation() == null) {
            address.setText("");
        } else {
            address.setText(person.getLocation().value);
        }

        initTags(person);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
