package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartEndDateTime;

// TODO card design
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
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyTask person, int displayedIndex) {
        super(FXML);
        name.setText(person.getName().value);
        id.setText(displayedIndex + ". ");
        phone.setText("No deadline!"); // TODO
        initDeadline(person);
        address.setText(""); // TODO
        email.setText(""); // TODO

        // TODO make sure both address and email are available though
        // and also SLAP
        if (person.getStartEndDateTime().isPresent()) {
            StartEndDateTime startEndDateTime = person.getStartEndDateTime().get();
            address.setText("Start Date: "
                    + startEndDateTime.getStartDateTime().format(ParserUtil.DATE_TIME_FORMAT));
            email.setText("End Date: "
                    + startEndDateTime.getEndDateTime().format(ParserUtil.DATE_TIME_FORMAT));
        }

        initTags(person);
    }

    private void initDeadline(ReadOnlyTask person) {
        assert person.getDeadline() != null;
        if (person.getDeadline().isPresent()) {
            phone.setText("Deadline: " + person.getDeadline().get().getValue().format(ParserUtil.DATE_TIME_FORMAT));
        }
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
