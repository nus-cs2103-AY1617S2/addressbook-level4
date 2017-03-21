package project.taskcrusher.ui;

import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import project.taskcrusher.model.task.ReadOnlyTask;

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

    public TaskListCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getTaskName().toString());
        id.setText(displayedIndex + ". ");
        showDeadline(task);
        showPriority(task);
        showDescription(task);

        initTags(task);
    }

    private void showDescription(ReadOnlyTask task) {
        description.setText(task.getDescription().toString());
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            deadline.setText(sdf.format(task.getDeadline().getDate().get()));
            //deadline.setText(MESSAGE_DEADLINE_BY + task.getDeadline().toString());
        } else {
            deadline.setText(MESSAGE_NO_DEADLINE);
        }
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
