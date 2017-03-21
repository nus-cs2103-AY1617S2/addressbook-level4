package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Note;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label note;
    @FXML
    private Label status;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex, boolean showIndex) {
        super(FXML);

        if (showIndex) id.setText(displayedIndex + ". ");

        name.setText(task.getName().fullName);
        setPriorityView(task);
        note.setText(task.getNote().map(Note::toString).orElse(""));
        status.setText(task.getStatus().value);
        startTime.setText(task.getStartTime().map(DateTime::toString).orElse(""));
        endTime.setText(task.getEndTime().map(DateTime::toString).orElse(""));
        initTags(task);
    }

    private void setPriorityView(ReadOnlyTask task) {
        String text = task.getPriority()
                .map(Priority::toString)
                .map(String::toUpperCase)
                .orElse("");

        priority.setText(text);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
