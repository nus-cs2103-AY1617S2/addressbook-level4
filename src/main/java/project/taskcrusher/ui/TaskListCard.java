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
    private static final String PRIORITY_PREPEND = " ";

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

    public TaskListCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().toString());
        id.setText(displayedIndex + ". ");
        showDeadline(task);
        showPriority(task);
        showDescription(task);
        displayComplete(task);

        initTags(task);
    }

    private void displayComplete(ReadOnlyTask task) {
        if (!task.isComplete()) {
            tickIcon.setVisible(false);
        }
    }

    private void showDescription(ReadOnlyTask task) {
        if (task.getDescription().hasDescription()) {
            description.setText("// " + task.getDescription().toString());
        } else {
            description.setText("");
        }
    }

    private void showPriority(ReadOnlyTask task) {
        if (task.getPriority().hasPriority()) {
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < Integer.parseInt(task.getPriority().toString()); i++) {
                stars.append("*");
            }
            priority.setText(PRIORITY_PREPEND + stars.toString());
        } else {
            priority.setText("");
        }
    }

    private void showDeadline(ReadOnlyTask task) {
        if (task.getDeadline().hasDeadline()) {
            deadline.setText(MESSAGE_DEADLINE_BY +
                    DateUtilApache.deadlineAsStringForUi(task.getDeadline().getDate().get()));
        } else {
            deadline.setText(MESSAGE_NO_DEADLINE);
        }
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
