package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label startTime;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane labels;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        title.setText(task.getTitle().title);
        id.setText(displayedIndex + ". ");
        if (task.getStartTime() != null) {
            startTime.setText("Start: " + task.getStartTime().toString());
        }
        deadline.setText("End: " + task.getDeadline().toString());
        initLabels(task);
    }

    private void initLabels(ReadOnlyTask task) {
        task.getLabels().forEach(label -> labels.getChildren().add(new Label(label.labelName)));
    }
}
