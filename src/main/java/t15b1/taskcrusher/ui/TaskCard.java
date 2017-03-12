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
        name.setText(task.getTaskName().taskName);
        id.setText(displayedIndex + ". ");
        showDeadlineIfExists(task);
        showPriorityIfExists(task);
        showDescriptionIfExists(task);
            
        initTags(task);
    }

    private void showDescriptionIfExists(ReadOnlyTask task) {
        if(task.getDescription().hasNoDescription())
            description.setText("");
        else
            description.setText(task.getDescription().value);
    }

    private void showPriorityIfExists(ReadOnlyTask task) {
        //TODO: make it nicer to show the priority just like how the tags appear
        if(task.getPriority().isDefaultPriority())
            priority.setText("");
        else
            priority.setText(PRIORITY_PREPEND + task.getPriority().value);
    }

    private void showDeadlineIfExists(ReadOnlyTask task) {
        if(task.getDeadline().hasNoDeadline())
            deadline.setText(NO_DEADLINE);
        else 
            deadline.setText(DEADLINE_BY + task.getDeadline().value);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
