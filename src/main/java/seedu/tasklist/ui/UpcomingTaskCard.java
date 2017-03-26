package seedu.tasklist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.ReadOnlyDeadlineTask;
import seedu.tasklist.model.task.ReadOnlyEventTask;
import seedu.tasklist.model.task.ReadOnlyTask;

//@@author A0143355J
public class UpcomingTaskCard extends UiPart<Region> {

    private static final String FXML = "UpcomingTaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    @FXML
    private Label startTime;
    @FXML
    private Label endTime;

    public UpcomingTaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        String taskType = task.getType();
        switch (taskType) {
        case FloatingTask.TYPE:
            startTime.setVisible(false);
            endTime.setVisible(false);
            break;
        case DeadlineTask.TYPE:
            startTime.setText("Due: ");
            endTime.setText(((ReadOnlyDeadlineTask) task).getTime());
            break;
        case EventTask.TYPE:
            startTime.setText(((ReadOnlyEventTask) task).getStartTime());
            endTime.setText(((ReadOnlyEventTask) task).getEndTime());
        }
    }
}
