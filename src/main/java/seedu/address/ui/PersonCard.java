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
    private HBox startEndOnly;
    @FXML
    private HBox deadlineOnly;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyTask person, int displayedIndex) {
        super(FXML);
        name.setText(person.getName().value);
        id.setText(displayedIndex + ". ");

        // TODO make sure both address and email are available though
        // and also SLAP
        if (person.getStartEndDateTime().isPresent()) {
            startEndOnly.setVisible(true);
            deadlineOnly.setVisible(false);
            StartEndDateTime startEndDateTime = person.getStartEndDateTime().get();
            startDate.setText(startEndDateTime.getStartDateTime().format(ParserUtil.DATE_TIME_FORMAT));
            endDate.setText(startEndDateTime.getEndDateTime().format(ParserUtil.DATE_TIME_FORMAT));
        } else if (person.getDeadline().isPresent()) {
            startEndOnly.setVisible(false);
            deadlineOnly.setVisible(true);
            deadline.setText(person.getDeadline().get().getValue().format(ParserUtil.DATE_TIME_FORMAT));
        } else {
            startEndOnly.setVisible(false);
            deadlineOnly.setVisible(false);
        }

        initTags(person);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
