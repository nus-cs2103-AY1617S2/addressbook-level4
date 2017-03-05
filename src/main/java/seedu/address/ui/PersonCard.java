package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyTask;

public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane labels;

    public PersonCard(ReadOnlyTask person, int displayedIndex) {
        super(FXML);
        name.setText(person.getTitle().title);
        id.setText(displayedIndex + ". ");
        deadline.setText(person.getDeadline().value.toString());
        initLabels(person);
    }

    private void initLabels(ReadOnlyTask person) {
        person.getLabels().forEach(label -> labels.getChildren().add(new Label(label.labelName)));
    }
}
