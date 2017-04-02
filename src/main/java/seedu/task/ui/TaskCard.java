package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label instruction;
    @FXML
    private Label priority;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane recurringTag;
    @FXML
    private FlowPane eventTag;
    @FXML
    private FlowPane overdueTag;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        title.setText(task.getTitle().title);
        id.setText(displayedIndex + ". ");
        date.setText(task.getDeadline().toString());
        instruction.setText(task.getInstruction().value);
        priority.setText(task.getPriority().toString());
        initTags(task);

        if (task.getDeadline().isOverdue()) {
            initOverdueTag();
        }
        if (task.getDeadline().isRecurring()) {
            initRecurTag();
        }
        if (task.getDeadline().isFromTo()) {
            initEventTag();
        }
    }

    private void initOverdueTag() {
        overdueTag.setStyle("-fx-background-color: #d12b2b;");
    }

    private void initEventTag() {
        Label eventLabel = new Label("E");
        eventLabel.setStyle("-fx-text-fill: black;"
                + "-fx-background-color: white;"
                + "-fx-font-family: Arial;"
                + "-fx-font-weight: bold;"
        );
        eventTag.getChildren().add(eventLabel);
    }

    private void initRecurTag() {
        Label recurringLabel = new Label("R");
        recurringLabel.setStyle("-fx-text-fill: black;"
                + "-fx-background-color: white;"
                + "-fx-font-family: Arial;"
                + "-fx-font-weight: bold;"
        );
        recurringTag.getChildren().add(recurringLabel);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
