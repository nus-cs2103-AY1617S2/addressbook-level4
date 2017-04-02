package seedu.taskboss.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskboss.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priorityLevel;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private Label information;
    @FXML
    private Label recurrence;
    @FXML
    private FlowPane categories;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        priorityLevel.setText(task.getPriorityLevel().value);
        startDateTime.setText(task.getStartDateTime().value);
        endDateTime.setText(task.getEndDateTime().value);
        information.setText(task.getInformation().value);
        recurrence.setText(task.getRecurrence().toString());

        initCategories(task);
    }

    private void initCategories(ReadOnlyTask task) {
        task.getCategories().forEach(category -> categories.getChildren().add(new Label(category.categoryName)));
    }
}
