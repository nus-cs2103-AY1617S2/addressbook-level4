package project.taskcrusher.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import project.taskcrusher.model.shared.DateUtilApache;
import project.taskcrusher.model.task.ReadOnlyTask;

//@@author A0127737X
/**
 * Controller for TaskListCard.fxml. Reads a ReadOnlyTask and create the layout accordingly.
 */
public class TaskListCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String MESSAGE_NO_DEADLINE = "no deadline";
    private static final String MESSAGE_DEADLINE_BY = "By ";
    private static final String OVERDUE_STYLE_CLASS = "overdue";

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

    public TaskListCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().toString());
        name.setMinWidth(Region.USE_PREF_SIZE);
        id.setText(displayedIndex + ". ");
        showDeadline(task);
        showPriority(task);
        showDescription(task);
        displayComplete(task);
        displayOverdueStatusIfAny(task);

        initTags(task);
    }

    private void displayComplete(ReadOnlyTask task) {
        if (!task.isComplete()) {
            tickIcon.setVisible(false);
        }
    }

    private void displayOverdueStatusIfAny(ReadOnlyTask task) {
        if (task.isOverdue()) {
            overdueIcon.setVisible(true);
            overdueIcon.setManaged(true);
            deadline.setStyle("-fx-text-fill: red"); //should not be done this way
        } else {
            overdueIcon.setVisible(false);
            overdueIcon.setManaged(false);
        }
    }

    private void showDescription(ReadOnlyTask task) {
        if (task.getDescription().hasDescription()) {
            description.setText(task.getDescription().toString());
            description.setMinWidth(Region.USE_PREF_SIZE);
        } else {
            description.setVisible(false);;
        }
    }

    private void showPriority(ReadOnlyTask task) {
        priority.setText("p=" + task.getPriority().priority);
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
        if (task.getDeadline().hasDeadline()) {
            deadline.setText(MESSAGE_DEADLINE_BY +
                    DateUtilApache.deadlineAsStringForUi(task.getDeadline().getDate().get()));
        } else {
            deadline.setText(MESSAGE_NO_DEADLINE);
        }
        deadline.setMinWidth(Region.USE_PREF_SIZE);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
