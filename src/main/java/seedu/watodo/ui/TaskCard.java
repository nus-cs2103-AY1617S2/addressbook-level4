package seedu.watodo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.watodo.model.task.ReadOnlyTask;

//@@author A0139845R-reused
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label status;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private FlowPane tags;


    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        description.setText(task.getDescription().fullDescription);
        id.setText(displayedIndex + ". ");

        if (task.getStartDate() != null) {
            startDate.setText(task.getStartDate().toString());
        } else {
            startDate.setText("");
        }

        if (task.getEndDate() != null) {
            endDate.setText(task.getEndDate().toString());
        } else {
            endDate.setText("");
        }

        status.setText(task.getStatus().toString());
        if (task.getStatus().toString().equals("Done")) {
            cardPane.setStyle("-fx-background-color: #2bba36;");
        }

        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
