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
public class EventTaskCard extends UiPart<Region> {

    private static final String FXML = "EventTaskCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskname;
    @FXML
    private Label id;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private Label markedCompleted;
    @FXML
    private FlowPane categories;

    private final StringProperty empty = new SimpleStringProperty("");
    private final StringProperty completed = new SimpleStringProperty("Completed");

    public EventTaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        taskname.setText(task.getTaskName().fullTaskName);
        id.setText(displayedIndex + ". ");

        if (isMultipleDateEvent(task)) {
            setMultipleDateEventTaskDetails(task);
        }

        if (isSameDateEvent(task)) {
            setSameDateEventTaskDetails(task);
        }

        setMarkedCompletedLabel(task);
        initCategory(task);
    }

    /**
     * @param task
     */
    private void setMarkedCompletedLabel(ReadOnlyTask task) {
        if (task.getIsMarkedAsComplete()) {
            markedCompleted.textProperty().bind(completed);
        } else {
            markedCompleted.textProperty().bind(empty);
        }
    }

    /**
     * @param task
     */
    private void setSameDateEventTaskDetails(ReadOnlyTask task) {
        startDateTime.setText("Date: " + task.getStartDate().value);
        endDateTime.setText(
                "Start Time: " + task.getStartTime().value + "   " + "End Time: " + task.getEndTime().value);
    }

    /**
     * @param task
     */
    private void setMultipleDateEventTaskDetails(ReadOnlyTask task) {
        startDateTime.setText(
                "Start Date: " + task.getStartDate().value + "   " + "Start Time: " + task.getStartTime().value);
        endDateTime.setText("End Date: " + "  " + task.getEndDate().value + "   " + "End Time: " + "  "
                + task.getEndTime().value);
    }

    private boolean isMultipleDateEvent(ReadOnlyTask task) {
        if (!task.getStartDate().value.equals(task.getEndDate().value)
                && !task.getStartDate().value.equals("EMPTY_FIELD")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSameDateEvent(ReadOnlyTask task) {
        if ((task.getStartDate().value).equals(task.getEndDate().value)
                && (!task.getStartDate().value.equals("EMPTY_FIELD"))) {
            return true;
        } else {
            return false;
        }
    }

    private void initCategory(ReadOnlyTask task) {
        if (!task.getCategories().asObservableList().isEmpty()) {
            for (int index = 0; task.getCategories().asObservableList().size() != index; index++) {
                Label label = stylePriorityCategories(task, index);

                categories.getChildren().add(label);
            }
        }
    }

    /**
     * @param task
     * @param index
     * @return
     */
    private Label stylePriorityCategories(ReadOnlyTask task, int index) {
        String category = task.getCategories().asObservableList().get(index).categoryName;
        Label label = new Label(category);
        if (category.equalsIgnoreCase("High")) {
            label.setStyle("-fx-background-color: red");
        }
        if (category.equalsIgnoreCase("Medium")) {
            label.setStyle("-fx-background-color: orange");
        }
        if (category.equalsIgnoreCase("Low")) {
            label.setStyle("-fx-background-color: lightblue");
        }
        return label;
    }
}
