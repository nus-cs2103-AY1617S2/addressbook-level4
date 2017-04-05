package seedu.jobs.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.jobs.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private VBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startTime;
    @FXML
    private Label description;
    @FXML
    private Label endTime;
    @FXML
    private FlowPane tags;
    @FXML
    private Pane status;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().getName());
        id.setText(displayedIndex + ". ");
        startTime.setText(task.getStartTime().value);
        description.setText(task.getDescription().value);
        endTime.setText(task.getEndTime().value);
        initTags(task);
        initStatus(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void initStatus(ReadOnlyTask task) {
    	if (task.isCompleted()) {
    		status.getChildren().add(new Label("Complete"));
    	} else status.getChildren().add(new Label("In-progress"));
    }

}
