package seedu.tasklist.ui;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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
        setCompleted(task);
        setName(task);
        setId(displayedIndex);
        setComment(task);
        setTags(task);
        setPriority(task);
        setDate(task);

    }

    /*
     * Set image for completed tasks
     */
    private void setCompleted(ReadOnlyTask task) {
        Image tickImage = AppUtil.getImage(tickSource);
        GridPane.setHalignment(tickLogo, HPos.RIGHT);
        tickLogo.setTranslateX(-50);
        if (task.getStatus().value) {
            tickLogo.setImage(tickImage);
        } else {
            tickLogo.setVisible(false);
        }
    }

    /*
     * Set name for tasks
     */
    private void setName(ReadOnlyTask task) {
        name.setText(task.getName().fullName);
    }

    /*
     * Set ID for tasks
     */
    private void setId(int displayedIndex) {
        id.setText(displayedIndex + ". ");
    }

    /*
     * Set comment for tasks
     */
    private void setComment(ReadOnlyTask task) {
        String taskComment = task.getComment().value;
        if ("NIL".equals(taskComment)) {
            comment.setVisible(false);
        } else {
            comment.setText(task.getComment().value);
        }
    }

    /*
     * Set tags for tasks
     */
    private void setTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /*
     * Set the priority level
     */
    private void setPriority(ReadOnlyTask task) {
        String priorityLevel = task.getPriority().value;
        switch(priorityLevel) {
        case Priority.PRIORITY_HIGH:
            priority.setStyle("-fx-background-color: #ff5050; -fx-text-fill: black;"
                    + " -fx-border-color: #ff5050; -fx-padding:2px");
            break;
        case Priority.PRIORITY_MEDIUM:
            priority.setStyle("-fx-background-color: #ff9900; -fx-text-fill: black;"
                    + " -fx-border-color: #ff9900; -fx-padding:2px");
            break;
        case Priority.PRIORITY_LOW:
            priority.setStyle("-fx-background-color: #22d64a; -fx-text-fill: black;"
                    + " -fx-border-color: #22d64a; -fx-padding:2px");
            break;
        case Priority.PRIORITY_NIL:
            priority.setVisible(false);
            break;
        default:
            assert false;
        }
        priority.setText(priorityLevel.toUpperCase());
        priority.setTranslateX(20);
    }

    /*
     * Set the startDate and endDate or deadline depending on the type of task
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
        default:
            assert false;
        }
    }
}
