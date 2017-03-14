package seedu.tasklist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.ReadOnlyDeadlineTask;
import seedu.tasklist.model.task.ReadOnlyEventTask;
import seedu.tasklist.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    @FXML
    private Label comment;

    @FXML
    private Label priority;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;

    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        String taskType = task.getType();
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        comment.setText(task.getComment().value);
        priority.setText("Priority: " + task.getPriority().value);
        initTags(task);
        switch(taskType) {
        case DeadlineTask.TYPE:
            startDate.setText("Deadline: " + ((ReadOnlyDeadlineTask) task).getDeadlineString());
            endDate.setVisible(false);
            break;
        case EventTask.TYPE:
            startDate.setText("Start Date: " + ((ReadOnlyEventTask) task).getStartDateString());
            endDate.setText("End Date: " + ((ReadOnlyEventTask) task).getEndDateString());
            break;
        case FloatingTask.TYPE:
            startDate.setVisible(false);
            endDate.setVisible(false);
            break;
        }

    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
