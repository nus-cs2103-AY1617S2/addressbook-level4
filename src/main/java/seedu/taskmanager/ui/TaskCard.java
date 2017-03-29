package seedu.taskmanager.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskmanager.model.task.ReadOnlyTask;

//@@author A0142418L
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskname;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label endDate;
    @FXML
    private Label toLabel;
    @FXML
    private Label markedCompleted;
    @FXML
    private FlowPane categories;

    private final StringProperty empty = new SimpleStringProperty("");
    private final StringProperty completed = new SimpleStringProperty("Completed");
    private final StringProperty to = new SimpleStringProperty("to");

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        taskname.setText(task.getTaskName().fullTaskName);
        id.setText(displayedIndex + ". ");
        if ((task.getStartDate().value).equals("EMPTY_FIELD")) {
            startDate.textProperty().bind(empty);
        } else {
            if ((task.getStartDate().value).equals(task.getEndDate().value)) {
                startDate.setText("Date: " + task.getStartDate().value);
            } else {
                startDate.setText("Start Date: " + task.getStartDate().value);
            }
        }
        if ((task.getStartTime().value).equals("EMPTY_FIELD")) {
            startTime.textProperty().bind(empty);
        } else {
            startTime.setText("Start Time: " + task.getStartTime().value);
        }
        if ((task.getEndDate().value).equals("EMPTY_FIELD")) {
            endDate.textProperty().bind(empty);
        } else {
            if ((task.getStartDate().value).equals(task.getEndDate().value)) {
                endDate.textProperty().bind(empty);
            } else {
                endDate.setText("End Date: " + task.getEndDate().value);
            }
        }
        if ((task.getEndTime().value).equals("EMPTY_FIELD")) {
            endTime.textProperty().bind(empty);
        } else {
            endTime.setText("End Time: " + task.getEndTime().value);
        }

        toLabel.textProperty().bind(empty);
        if (!(task.getEndTime().value).equals("EMPTY_FIELD") && !(task.getStartTime().value).equals("EMPTY_FIELD")) {
            toLabel.textProperty().bind(to);
        }
        if (task.getIsMarkedAsComplete()) {
            markedCompleted.textProperty().bind(completed);
        } else {
            markedCompleted.textProperty().bind(empty);
        }
        initCategory(task);
    }

    private void initCategory(ReadOnlyTask task) {
        task.getCategories().forEach(category -> categories.getChildren().add(new Label(category.categoryName)));
    }

}
