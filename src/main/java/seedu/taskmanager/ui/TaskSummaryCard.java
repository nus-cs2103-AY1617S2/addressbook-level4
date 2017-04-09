package seedu.taskmanager.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskmanager.model.task.ReadOnlyTask;

// @@author A0140032E
public class TaskSummaryCard extends UiPart<Region> {

    private static final String FXML = "TaskListSummaryCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label description;

    public TaskSummaryCard(ReadOnlyTask task) {
        super(FXML);
        title.setText(task.getTitle().value);
        description.setText(task.getDescription().isPresent() ? task.getDescription().get().value : "");
    }
}
