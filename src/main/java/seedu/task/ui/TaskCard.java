package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.Group;
import seedu.task.model.task.ReadOnlyTask;

//@@author A0164032U
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

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

    public TaskCard(ReadOnlyTask person, int displayedIndex) {
        super(FXML);

        name.setText(person.getName().fullName);
        id.setText(displayedIndex + ". ");

        if (person.getEndDate() == null || person.getEndDate().getInputValue() == null) {
            end.setVisible(false);
        } else {
            end.setText("Ends:   " + person.getEndDate());
        }

        if (person.getStartDate() == null || person.getStartDate().getInputValue() == null) {
            start.setVisible(false);
        } else {
            start.setText("Starts: " + person.getStartDate());
        }

        if (!person.getGroup().value.equals(Group.GROUP_ID_HIDDEN)) {
            group.setText(person.getGroup().value);
        } else {
            group.setVisible(false);
        }

        if (person.hasPassed()) {
            setStyleToIndicateEndDatePassed();
        }

        try {
            if (person.getTags().contains(new Tag(Tag.TAG_COMPLETE))) {
                setStyleToIndicateComplete();
            } else {
                setStyleToIndicateIncomplete();
            }
        } catch (IllegalValueException e) {
        }

        cardPane.autosize();
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateEndDatePassed() {
        cardPane.getStyleClass().add(Ui.ERROR_STYLE_CLASS);
    }

    private void setStyleToIndicateComplete() {
        cardPane.getStyleClass().add(Ui.COMPLETE_STYLE_CLASS);
    }

    private void setStyleToIndicateIncomplete() {
        cardPane.getStyleClass().add(Ui.INCOMPLETE_STYLE_CLASS);
    }
}
