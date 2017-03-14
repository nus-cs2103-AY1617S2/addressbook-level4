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
    private static final String PRIORITY_PREPEND = "              Priority = ";

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
        //TODO: make it nicer to show the priority just like how the tags appear
    	priority.setText(PRIORITY_PREPEND + task.getPriority().toString());
    }

    private void showDeadline(ReadOnlyTask task) {
    	deadline.setText(task.getDeadline().toString());
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
