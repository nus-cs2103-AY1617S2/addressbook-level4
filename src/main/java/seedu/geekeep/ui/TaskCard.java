package seedu.geekeep.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.geekeep.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

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

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getTitle().fullTitle);
        id.setText("#" + displayedIndex + " ");

        if (task.getEndDateTime() != null && task.getStartDateTime() != null) {
            phone.setText(task.getStartDateTime() + " until " + task.getEndDateTime());
        } else if (task.getEndDateTime() != null && task.getStartDateTime() == null) {
            phone.setText(task.getEndDateTime().value);
        } else {
            phone.setText(null);
        }

        if (task.getLocation() == null) {
            address.setText("");
        } else {
            address.setText(task.getLocation().value);
        }

        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
