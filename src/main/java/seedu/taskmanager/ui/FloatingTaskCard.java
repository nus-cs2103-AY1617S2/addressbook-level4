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
public class FloatingTaskCard extends UiPart<Region> {

    private static final String FXML = "FloatingTaskCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskname;
    @FXML
    private Label id;
    @FXML
    private Label markedCompleted;
    @FXML
    private FlowPane categories;

    private final StringProperty empty = new SimpleStringProperty("");
    private final StringProperty completed = new SimpleStringProperty("Completed");

    public FloatingTaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        taskname.setText(task.getTaskName().fullTaskName);
        id.setText(displayedIndex + ". ");

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
