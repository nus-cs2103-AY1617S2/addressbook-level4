package seedu.tache.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tache.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label startDate;

    @FXML
    private Label endDate;

    @FXML
    private Label startTime;

    @FXML
    private Label endTime;

    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        id.setText(Integer.toString(displayedIndex) + ". ");
        name.setText(task.getName().toString());
        String defaultValue = "-";
        if (task.getStartDateTime().isPresent()) {
            startDate.setText(task.getStartDateTime().get().getDateOnly());
        } else {
            startDate.setText(defaultValue);
        }
        if (task.getEndDateTime().isPresent()) {
            endDate.setText(task.getEndDateTime().get().getDateOnly());
        } else {
            endDate.setText(defaultValue);
        }
        if (task.getStartDateTime().isPresent()) {
            startTime.setText(task.getStartDateTime().get().getTimeOnly());
        } else {
            startTime.setText(defaultValue);
        }
        if (task.getEndDateTime().isPresent()) {
            endTime.setText(task.getEndDateTime().get().getTimeOnly());
        } else {
            endTime.setText(defaultValue);
        }
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
