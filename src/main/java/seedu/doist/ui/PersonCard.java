package seedu.doist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.doist.model.task.ReadOnlyTask;

public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label desc;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label isFinished;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        desc.setText(task.getDescription().desc);
        id.setText(displayedIndex + ". ");
        priority.setText(task.getPriority().toString());
        isFinished.setText("finished: " + task.getFinishedStatus().toString());
        if (task.getStartDate() == null) {
            startTime.setText("No start time");
        } else {
            startTime.setText(task.getStartDate().toString());
        }
        if (task.getEndDate() == null) {
            endTime.setText("No end time");
        } else {
            endTime.setText(task.getEndDate().toString());
        }
        initTags(task);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
