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
        if(task.getPriority().isDefaultPriority())
            priority.setText("");
        else{
            StringBuilder stars = new StringBuilder();
            for(int i=0; i<Integer.parseInt(task.getPriority().value); i++)
                stars.append("*");
            priority.setText(PRIORITY_PREPEND + stars.toString());
        }
    }

    private void showDeadlineIfExists(ReadOnlyTask task) {
        if(!task.getDeadline().hasDeadline())
            deadline.setText(NO_DEADLINE);
        else
            deadline.setText(DEADLINE_BY + task.getDeadline().deadline);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
