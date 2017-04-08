package seedu.watodo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.watodo.model.task.ReadOnlyTask;

//@@author A0139845R-reused
public class ImportantTaskCard extends UiPart<Region> {

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


    public ImportantTaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        description.setText(task.getDescription().fullDescription);
        id.setText("! ");
        setStartDate(task);
        setEndDate(task);
        setStatus(task);
        initTags(task);
    }

    private void setStatus(ReadOnlyTask task) {
        status.setText(task.getStatus().toString());
        if (task.getStatus().toString().equals("Done")) {
            cardPane.setStyle("-fx-background-color: #5c5c5c;");
        }
    }

    private void setEndDate(ReadOnlyTask task) {
        if (task.getEndDate() != null) {
            endDate.setText("By: " + task.getEndDate());
        } else {
            endDate.setText("");
        }
    }

    private void setStartDate(ReadOnlyTask task) {
        if (task.getStartDate() != null) {
            startDate.setText("Start: " + task.getStartDate());
        } else {
            startDate.setText("");
        }
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
