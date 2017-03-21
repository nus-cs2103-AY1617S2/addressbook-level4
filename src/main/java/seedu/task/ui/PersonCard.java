package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.model.task.ReadOnlyTask;

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

    public PersonCard(ReadOnlyTask person, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ". ");
        if (person.getTaskName() != null) {
        	name.setText(person.getTaskName().toString());
        }
        if (person.getTaskDate() != null) {
        	phone.setText(person.getTaskDate().toString());
        }
        if (person.getTaskStartTime() != null && person.getTaskEndTime() != null) {
        	address.setText(person.getTaskStartTime().toString() + "-" + person.getTaskEndTime());
        }
        if (person.getTaskDescription() != null) {
        	email.setText(person.getTaskDescription().toString());
        }
    }
}
