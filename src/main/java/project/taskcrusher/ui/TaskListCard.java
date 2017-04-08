package project.taskcrusher.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import project.taskcrusher.commons.util.UiDisplayUtil;
import project.taskcrusher.model.task.ReadOnlyTask;

//@@author A0127737X
/**
 * Controller for TaskListCard.fxml. Reads a ReadOnlyTask and create the layout accordingly.
 */
public class TaskListCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label deadline;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView tickIcon;
    @FXML
    private ImageView overdueIcon;

    public TaskListCard(ReadOnlyTask task, int displayedIndex, boolean isOverdue) {
        super(FXML);
        showIdAndName(task, displayedIndex);
        showDeadline(task);
        showPriority(task);
        showDescription(task);
        displayCompleteTickIfApplicable(task);
        displayOverdueStatusIfApplicable(task, isOverdue);

        initTags(task);
    }

    private void showIdAndName(ReadOnlyTask task, int displayedIndex) {
        name.setText(task.getName().name);
        name.setMinWidth(Region.USE_PREF_SIZE);
        id.setText(displayedIndex + ". ");
    }

    private void displayCompleteTickIfApplicable(ReadOnlyTask task) {
        if (!task.isComplete()) {
            tickIcon.setVisible(false);
        }
    }

    private void displayOverdueStatusIfApplicable(ReadOnlyTask task, boolean isOverdue) {
        if (!task.isComplete() && isOverdue) {
            overdueIcon.setVisible(true);
            overdueIcon.setManaged(true);
            deadline.setStyle("-fx-text-fill: red"); //should not be done this way
        } else {
            overdueIcon.setVisible(false);
            overdueIcon.setManaged(false);
        }
    }

    private void showDescription(ReadOnlyTask task) {
        description.setText(task.getDescription().toString()); //still set the text even if empty for GuiTest
        if (task.getDescription().hasDescription()) {
            description.setMinWidth(Region.USE_PREF_SIZE);
        } else {
            description.setVisible(false);;
        }
    }

    private void showPriority(ReadOnlyTask task) {
        priority.setText(UiDisplayUtil.priorityForUi(task.getPriority()));
        switch (task.getPriority().priority) {
        case "1":
            priority.getStyleClass().add("priority-one");
            break;
        case "2":
            priority.getStyleClass().add("priority-two");
            break;
        case "3":
            priority.getStyleClass().add("priority-three");
            break;
        default:
            priority.setVisible(false);
            priority.setManaged(false);
        }
        priority.setMinWidth(Region.USE_PREF_SIZE);
    }

    private void showDeadline(ReadOnlyTask task) {
        deadline.setText(UiDisplayUtil.deadlineForUi(task.getDeadline()));
        deadline.setMinWidth(Region.USE_PREF_SIZE);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
