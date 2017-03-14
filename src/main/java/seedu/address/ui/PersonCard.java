package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

public class PersonCard extends UiPart<Region> {

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

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ". ");
        if (person.getTaskName() != null) {
        	name.setText(person.getTaskName().toString());
        }
        if (person.getDate() != null) {
        	phone.setText(person.getDate().toString());
        }
        if (person.getStartTime() != null && person.getEndTime() != null) {
        	address.setText(person.getStartTime().toString() + "-" + person.getEndTime());
        }
        if (person.getDescription() != null) {
        	email.setText(person.getDescription().toString());
        }
    }
}
