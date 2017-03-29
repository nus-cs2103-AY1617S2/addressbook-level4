package seedu.tasklist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.ReadOnlyDeadlineTask;
import seedu.tasklist.model.task.ReadOnlyEventTask;
import seedu.tasklist.model.task.ReadOnlyTask;

//@@author A0143355J
public class UpcomingTaskCard extends UiPart<Region> {

    private static final String FXML = "UpcomingTaskListCard.fxml";

    @FXML
    private Label name;

    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label connector;

    public UpcomingTaskCard(ReadOnlyTask task) {
        super(FXML);
        setName(task);
        setDate(task);
    }

    private void setName(ReadOnlyTask task) {
        name.setText(task.getName().fullName);
    }

    private void setDate(ReadOnlyTask task) {
        String taskType = task.getType();
        switch (taskType) {
        case DeadlineTask.TYPE:
            startDate.setText("Deadline :");
            connector.setText("- ");
            connector.setStyle("-fx-text-fill: white");
            endDate.setText(((ReadOnlyDeadlineTask) task).getDeadlineStringForUpcomingTask() + " ");
            break;
        case EventTask.TYPE:
            startDate.setText(((ReadOnlyEventTask) task).getStartDateStringForUpcomingTask() + " ");
            connector.setText("- ");
            endDate.setText(((ReadOnlyEventTask) task).getEndDateStringForUpcomingTask() + " ");
            break;
        default:
            assert false;
        }
    }
}
