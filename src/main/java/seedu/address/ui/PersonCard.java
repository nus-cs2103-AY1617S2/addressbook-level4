package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
<<<<<<< HEAD
import seedu.address.model.task.ReadOnlyTask;
=======
import seedu.address.model.person.ReadOnlyTask;
>>>>>>> parent of 9b5fb6b... test

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;

<<<<<<< HEAD
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
=======
    public PersonCard(ReadOnlyTask person, int displayedIndex) {
>>>>>>> parent of 9b5fb6b... test
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        date.setText(date.getDate().value);
        initTags(person);
    }

<<<<<<< HEAD
    private void initTags(ReadOnlyTask task) {
=======
    private void initTags(ReadOnlyTask person) {
>>>>>>> parent of 9b5fb6b... test
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
