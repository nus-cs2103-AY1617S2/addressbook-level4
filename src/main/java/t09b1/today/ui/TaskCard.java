package t09b1.today.ui;

import java.util.logging.Logger;

import com.jfoenix.controls.JFXProgressBar;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import t09b1.today.commons.core.LogsCenter;
import t09b1.today.commons.events.ui.JumpToListRequestEvent;
import t09b1.today.model.task.ReadOnlyTask;

/**
 * Controller for each card in the lists
 */
public class TaskCard extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskCard.class);

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
    @FXML
    private Label taskDate;
    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private JFXProgressBar overdueBar;
    @FXML
    private AnchorPane cardCell;

    // @@author A0144315N
    public TaskCard(ReadOnlyTask task, String displayedIndex) {
        super(FXML);
        name.setText(task.getName().toString());
        id.setText(displayedIndex + "");
        taskDate.setText(task.getTaskAbsoluteDateTime());
        initTags(task);
        tags.setAlignment(Pos.CENTER_LEFT);
        taskDate.setAlignment(Pos.BASELINE_RIGHT);
        progressBar.setVisible(task.isAnimated());
        logger.info("############################################TASK ISANIMATED:" + task.isAnimated());

        // Play progress bar animation
        if (task.getIsAnimated() == 1) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                    new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.progressProperty(), 1)));
            timeline.setCycleCount(1);
            timeline.play();
            task.setAnimation(0);
            // Scroll to the card
            raise(new JumpToListRequestEvent(task.getID()));
        } else if (task.getIsAnimated() > 1) {
            task.setAnimation(task.getIsAnimated() - 1);
        }

        // Change to Red if overdue(excluding those in the completed list)
        if (task.isOverdue() && !task.isDone()) {
            cardCell.setStyle("-fx-background-color: #ffdbe0;");
        }
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.getTagName())));
    }
}
