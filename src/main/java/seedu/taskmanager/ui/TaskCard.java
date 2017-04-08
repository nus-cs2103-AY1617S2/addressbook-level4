package seedu.taskmanager.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskmanager.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label description;
    @FXML
    private Label repeat;
    @FXML
    private Label endDate;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        title.setText(task.getTitle().value);
        id.setText(displayedIndex + ". ");
        // @@author A0140032E
        startDate.setText(task.getStartDate().isPresent() ? "Starts on " + task.getStartDate().get() : "");
        endDate.setText(task.getEndDate().isPresent() ? "Ends on " + task.getEndDate().get() : "");
        description.setText(task.getDescription().isPresent() ? task.getDescription().get().value : "");
        repeat.setText(task.getRepeat().isPresent() ? "Repeats: " + task.getRepeat().get() : "");
        // @@author
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
