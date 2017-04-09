package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

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

        if (person.getEndDate() == null) {
            end.setVisible(false);
            end.setStyle("-fx-font-size: 0pt;");
        } else {
            end.setText("Ends:   " + person.getEndDate());
        }

        if (person.getStartDate() == null) {
            start.setVisible(false);
            start.setStyle("-fx-font-size: 0pt;");
        } else {
            start.setText("Starts: " + person.getStartDate());
        }

        group.setText(person.getGroup().value);

        if (person.hasPassed()) {
            setStyleToIndicateEndDatePassed();
        }

        cardPane.autosize();
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateEndDatePassed() {
        cardPane.getStyleClass().add(Ui.ERROR_STYLE_CLASS);
    }
}
