package seedu.doist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.doist.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    CheckBox checkbox;
    @FXML
    private Label desc;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        checkbox.setDisable(true);
        checkbox.setStyle("-fx-opacity: 1");
        desc.setText(task.getDescription().desc);
        id.setText(displayedIndex + ". ");
        priority.setText(task.getPriority().toString());
        if (task.getStartDate() == null) {
            startTime.setText("");
        } else {
            startTime.setText(task.getStartDate().toString());
        }
        if (task.getEndDate() == null) {
            endTime.setText("");
        } else {
            endTime.setText(task.getEndDate().toString());
        }
        if (task.getFinishedStatus().getIsFinished()) {
            checkbox.setSelected(true);
        } else {
            checkbox.setSelected(false);
        }
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
