package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyPerson;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Date;
import seedu.address.model.task.StartDate;

//@@author A0164032U
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label sdate;
    @FXML
    private Label group;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        
        name.setText(person.getName().fullName);
        id.setText(displayedIndex + ". ");
        if (person.getDate().value.equals("00.00")) {
            date.setText("");
        } else {
            date.setText("End date: " + person.getDate().value);
        }

        if (person.getStartDate().value.equals("00.00")) {
            sdate.setText("");
        } else {
            sdate.setText("Start Date: " + person.getStartDate().value);
        }
        group.setText(person.getGroup().value);
        email.setText(person.getEmail().value);
        initTags(person);
    }

    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
