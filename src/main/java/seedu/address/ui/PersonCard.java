package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyPerson;

//@@author A0164032U
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String PASSED_HEX_COLOR = "#f08080";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label end;
    @FXML
    private Label start;
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
        
        if (person.getEndDate() == null) {
            end.setVisible(false);
        } else {
            end.setText("End date: " + person.getEndDate().value);
        }

        if (person.getStartDate() == null) {
            start.setVisible(false);
        } else {
            start.setText("Start Date: " + person.getStartDate().value);
        }
        
        group.setText(person.getGroup().value);
        initTags(person);
        
        if (person.hasPassed()) {
            cardPane.setStyle("-fx-background-color: " + PASSED_HEX_COLOR);
        }
    }

    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
