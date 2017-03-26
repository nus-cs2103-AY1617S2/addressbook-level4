package seedu.doist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.doist.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String START_TIME_TEXT = "Start: ";
    private static final String END_TIME_TEXT = "End: ";
    public static final String NORMAL_STYLE_CLASS = "normal";
    public static final String OVERDUE_STYLE_CLASS = "overdue";
    public static final String FINISHED_STYLE_CLASS = "finished";

    @FXML
    private HBox cardPane;
    @FXML
    private GridPane grid;
    @FXML
    private VBox sideBox;
    @FXML
    private VBox taskBox;
    @FXML
    CheckBox checkbox;
    @FXML
    private Label desc;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private FlowPane tags;

    //@@author A0140887W
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        setStyleToNormal();
        desc.setText(task.getDescription().desc);
        id.setText(displayedIndex + ". ");
        priority.setText(task.getPriority().toString());

        // Times
        if (task.getStartDate() == null) {
            startTime.setText("");
        } else {
            startTime.setText(START_TIME_TEXT + task.getStartDate().toString());
        }
        if (task.getEndDate() == null) {
            endTime.setText("");
        } else {
            endTime.setText(END_TIME_TEXT + task.getEndDate().toString());
        }

        // Finished
        checkbox.setDisable(true);
        checkbox.setStyle("-fx-opacity: 1");
        if (task.getFinishedStatus().getIsFinished()) {
            checkbox.setSelected(true);
            setStyleToFinished();
        } else {
            checkbox.setSelected(false);
        }

        // Tags
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Sets the card pane style to the style of a normal task
     */
    private void setStyleToNormal() {
        cardPane.getStyleClass().remove(OVERDUE_STYLE_CLASS);
        cardPane.getStyleClass().remove(FINISHED_STYLE_CLASS);
        // Only add style if don't already have
        if (!cardPane.getStyleClass().contains(NORMAL_STYLE_CLASS)) {
            cardPane.getStyleClass().add(NORMAL_STYLE_CLASS);
        }
    }

    /**
     * Sets the card pane style to the style of an overdue task
     */
    private void setStyleToOverdue() {
        cardPane.getStyleClass().remove(NORMAL_STYLE_CLASS);
        cardPane.getStyleClass().remove(FINISHED_STYLE_CLASS);
        // Only add style if don't already have
        if (!cardPane.getStyleClass().contains(OVERDUE_STYLE_CLASS)) {
            cardPane.getStyleClass().add(OVERDUE_STYLE_CLASS);
        }
    }

    /**
     * Sets the card pane style to the style of a normal task
     */
    private void setStyleToFinished() {
        cardPane.getStyleClass().remove(OVERDUE_STYLE_CLASS);
        cardPane.getStyleClass().remove(NORMAL_STYLE_CLASS);
        // Only add style if don't already have
        if (!cardPane.getStyleClass().contains(FINISHED_STYLE_CLASS)) {
            cardPane.getStyleClass().add(FINISHED_STYLE_CLASS);
        }
    }
}
