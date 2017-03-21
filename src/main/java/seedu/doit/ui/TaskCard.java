package seedu.doit.ui;

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
        //this.labelBullet = new Circle(4.0);
        switch(task.getPriority().value) {
        case "high": this.labelBullet.setFill(Color.RED); break;
        case "med": this.labelBullet.setFill(Color.ORANGE); break;
        case "low": this.labelBullet.setFill(Color.GREEN); break;
        default: this.labelBullet.setFill(Color.WHITE);
        }
        this.description.setText(task.getDescription().value);
        initTags(task);

        if (task.hasStartTime()) {
            this.deadline.setText(task.getStartTime().value + " - " + task.getDeadline().value);
        } else if (task.hasEndTime()) {
            this.deadline.setText(task.getDeadline().value);
        } else {
            this.deadline.setText("");
        }
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> this.tags.getChildren().add(new Label(tag.tagName)));
    }
}
