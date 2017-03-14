package t15b1.taskcrusher.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import t15b1.taskcrusher.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String NO_DEADLINE = "no deadline";
    private static final String DEADLINE_BY = "By ";
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

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
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
            for(int i=0; i<Integer.parseInt(task.getPriority().value); i++)
                stars.append("*");
            priority.setText(PRIORITY_PREPEND + stars.toString());
        } else {
            priority.setText(Task.NO_PRIORITY);
        }
    }

    private void showDeadline(ReadOnlyTask task) {
        if (task.getDeadline().hasDeadline()) {
            deadline.setText(DEADLINE_BY + task.getDeadline().toString());
        } else {
            deadline.setText(Deadline.NO_DEADLINE);
        }
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
