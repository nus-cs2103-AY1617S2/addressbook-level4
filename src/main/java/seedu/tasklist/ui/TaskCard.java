package seedu.tasklist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tasklist.commons.util.AppUtil;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.ReadOnlyDeadlineTask;
import seedu.tasklist.model.task.ReadOnlyEventTask;
import seedu.tasklist.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String tickSource = "/images/tick.png";

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
    private Label deadline;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private ImageView tickLogo;

    @FXML
    private FlowPane tags;

    //@@author A0143355J
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        Image tickImage = AppUtil.getImage(tickSource);
        tickLogo.setImage(tickImage);
        if (task.getStatus().value == false) {
            tickLogo.setVisible(false);
        }
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        comment.setText(task.getComment().value);
        priority.setText(task.getPriority().value.toUpperCase());
        priority.setTranslateX(20);
        initTags(task);
        setPriority(task);
        setDate(task);

    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /*
     * Sets the priority level
     */
    private void setPriority(ReadOnlyTask task) {
        String priorityLevel = task.getPriority().value;
        switch(priorityLevel) {
        case Priority.PRIORITY_HIGH:
            priority.setStyle("-fx-text-fill: #ff5050; -fx-border-color: #ff5050; -fx-padding: 2px");
            break;
        case Priority.PRIORITY_MEDIUM:
            priority.setStyle("-fx-text-fill: #ff9900; -fx-border-color: #ff9900; -fx-padding: 2px");
            break;
        case Priority.PRIORITY_LOW:
            priority.setStyle("-fx-text-fill: #33cc33; -fx-border-color: #33cc33; -fx-padding: 2px");
            break;

        case Priority.PRIORITY_NIL:
            priority.setVisible(false);;
        }
    }

    /*
     * Sets the startDate and endDate or deadline depending on the type of task
     */
    private void setDate(ReadOnlyTask task) {
        String taskType = task.getType();
        switch(taskType) {
        case DeadlineTask.TYPE:
            deadline.setText("Deadline: " + ((ReadOnlyDeadlineTask) task).getDeadlineString());
            startDate.setVisible(false);
            endDate.setVisible(false);
            break;
        case EventTask.TYPE:
            deadline.setVisible(false);
            startDate.setText("Start Date: " + ((ReadOnlyEventTask) task).getStartDateString());
            endDate.setText("End Date: " + ((ReadOnlyEventTask) task).getEndDateString());
            break;
        case FloatingTask.TYPE:
            deadline.setVisible(false);
            startDate.setVisible(false);
            endDate.setVisible(false);
            break;
        }
    }
}
