package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane labels;

    public TaskCard(ReadOnlyTask person, int displayedIndex) {
        super(FXML);
        title.setText(person.getTitle().title);
        id.setText(displayedIndex + ". ");
        deadline.setText(person.getDeadline().value);
        initLabels(person);
    }

    private void initLabels(ReadOnlyTask person) {
        person.getLabels().forEach(label -> labels.getChildren().add(new Label(label.labelName)));
    }
}
