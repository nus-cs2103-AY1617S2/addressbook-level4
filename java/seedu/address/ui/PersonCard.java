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
    private Label taskName;
    @FXML
    private Label date;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label description;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        taskName.setText(person.getTaskName().toString());
        date.setText(person.getDate().toString());
        startTime.setText(person.getStartTime().toString());
        endTime.setText(person.getEndTime().toString());
        description.setText(person.getDescription().toString());
    }
}
