package seedu.doit.ui;
// @@author: A0160076L
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import seedu.doit.model.item.ReadOnlyTask;


public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane tags;
    @FXML
    private Circle labelBullet;


    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.name.setText(task.getName().fullName);
        this.id.setText(displayedIndex + ". ");
        switch(task.getPriority().value) {
        case "high": this.labelBullet.setFill(Color.RED); break;
        case "med": this.labelBullet.setFill(Color.ORANGE); break;
        case "low": this.labelBullet.setFill(Color.GREEN); break;
        }
        this.description.setText(task.getDescription().value);
        initTags(task);
        if (task.hasStartTime()) {
            this.deadline.setText(task.getStartTime().value + " - " + task.getDeadline().value);
            if (ifOverdue(task.getDeadline().value)) {
                this.cardPane.setStyle("-fx-border-color:#8B0000; "
                        + "-fx-border-width:1 1 1 30;");
            }
        } else if (task.hasEndTime()) {
            this.deadline.setText(task.getDeadline().value);
            if (ifOverdue(task.getDeadline().value)) {
                this.cardPane.setStyle("-fx-border-color:#8B0000; -fx-border-width:1 1 1 30");
            }
        } else {
            this.deadline.setText("");
        }
    }

    public boolean ifOverdue(String dateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
        LocalDateTime dateTimeToCompare = LocalDateTime.parse(dateTime, formatter);
        return !currentDateTime.isBefore(dateTimeToCompare);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> this.tags.getChildren().add(new Label(tag.tagName)));
    }
}
