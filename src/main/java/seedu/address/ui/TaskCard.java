package seedu.address.ui;

import java.util.logging.Logger;

import com.jfoenix.controls.JFXProgressBar;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.task.ReadOnlyTask;

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

    public TaskCard(ReadOnlyTask task, String displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + "");
        taskDate.setText(task.getTaskAbsoluteDateTime());
        initTags(task);
        tags.setAlignment(Pos.CENTER_LEFT);
        taskDate.setAlignment(Pos.BASELINE_RIGHT);
        progressBar.setVisible(task.isAnimated());
        logger.info("############################################TASK ISANIMATED:" + task.isAnimated());
        if (task.isAnimated()) {
            // Play progress bar animation
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(progressBar.visibleProperty(), true),
                            new KeyValue(progressBar.progressProperty(), 0)),
                    new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.visibleProperty(), false),
                            new KeyValue(progressBar.progressProperty(), 1)));
            timeline.setCycleCount(2);
            timeline.play();
            task.setAnimation(false);
        }
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
