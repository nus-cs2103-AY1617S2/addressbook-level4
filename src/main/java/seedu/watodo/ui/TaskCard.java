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
        setStartDate(task);
        setEndDate(task);
        setStatus(task);
        initTags(task);
    }

    /**
     * Changes status text and colour based on status of the task
     * @param task
     */
    private void setStatus(ReadOnlyTask task) {
        status.setText(task.getStatus().toString());
        if (task.getStatus().toString().equals("Done")) {
            cardPane.setStyle("-fx-background-color: #5c5c5c;");
            description.setStyle("-fx-text-fill: #6dc006;");
            setLabelsColourGreen();

        }
    }

    private void setLabelsColourGreen() {
        status.setStyle("-fx-text-fill: #6dc006;");
        id.setStyle("-fx-text-fill: #6dc006;");
        startDate.setStyle("-fx-text-fill: #6dc006;");
        endDate.setStyle("-fx-text-fill: #6dc006;");
    }

    /**
     * Gets the end date of the task and updates the card
     * If no date, blank the label
     * @param task
     */
    private void setEndDate(ReadOnlyTask task) {
        if (task.getEndDate() != null) {
            endDate.setText(task.getEndDate().toString());
        } else {
            endDate.setText("");
        }
    }

    /**
     * Gets the start date of the task and updates the card
     * If no date, blank the label
     * @param task
     */
    private void setStartDate(ReadOnlyTask task) {
        if (task.getStartDate() != null) {
            startDate.setText(task.getStartDate().toString());
        } else {
            startDate.setText("");
        }
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
