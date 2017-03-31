//@@author A0148037E
package seedu.geekeep.ui;

import com.jfoenix.controls.JFXCheckBox;

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
    private Label title;
    @FXML
    private JFXCheckBox done;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label descriptionOfTask;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        title.setText(task.getTitle().title);
        id.setText("#" + displayedIndex + " ");
        done.setSelected(task.isDone());
        if (task.getEndDateTime() != null && task.getStartDateTime() != null) {
            date.setText(task.getStartDateTime() + " until " + task.getEndDateTime());
        } else if (task.getEndDateTime() != null && task.getStartDateTime() == null) {
            date.setText(task.getEndDateTime().value);
        } else {
            date.setText(null);
        }

        if (task.getDescriptoin() == null) {
            descriptionOfTask.setText("");
        } else {
            descriptionOfTask.setText(task.getDescriptoin().value);
        }

        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
