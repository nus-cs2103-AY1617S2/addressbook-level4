package seedu.taskList.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskList.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    @FXML
    private Label address;

    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask person, int displayedIndex) {
        super(FXML);
        name.setText(person.getName().fullName);
        id.setText(displayedIndex + ". ");
        address.setText(person.getAddress().value);
        initTags(person);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
