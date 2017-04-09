package seedu.doit.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.doit.model.item.ReadOnlyTask;


public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String tickSource = "/images/tick.png";
    private static final String highSource = "/images/high.png";
    private static final String medSource = "/images/med.png";
    private static final String descriptionSource = "/images/descriptionSign.png";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private ImageView descriptionSign;
    @FXML
    private Label deadline;
    @FXML
    private Label startTime;
    @FXML
    private Label to;
    @FXML
    private FlowPane tags;
    @FXML
    private Circle labelBullet;


    private Image tick;
    private Image high;
    private Image med;
    private Image description;

  //@@author: A0160076L
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.name.setText(task.getName().fullName);
        this.id.setText(displayedIndex + ". ");
        setStartTimeEndTime(task);
        setTimeColor(task);
        setLabelBullet(task);
        setDescriptionSign(task);
        initTags(task);
    }

    public boolean isOverdue(String dateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        LocalDateTime dateTimeToCompare = LocalDateTime.parse(dateTime, formatter);
        return !currentDateTime.isBefore(dateTimeToCompare);
    }

    public boolean isDueSoon(String dateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
        LocalDateTime dateTimeToCompare = LocalDateTime.parse(dateTime, formatter);
        return !currentDateTime.isBefore(dateTimeToCompare.minusDays(3));
    }

    private void setDescriptionSign(ReadOnlyTask task) {
        this.description = new Image(descriptionSource);
        this.descriptionSign.setImage(this.description);

        if (task.getDescription() != null && !task.getDescription().toString().equals("")) {
            this.descriptionSign.setVisible(true);
        } else {
            this.descriptionSign.setVisible(false);
        }
    }
    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> this.tags.getChildren().add(new Label(tag.tagName)));
    }

    private void setLabelBullet(ReadOnlyTask task) {
        this.labelBullet.setOpacity(1);
        this.tick = new Image(tickSource);
        this.high = new Image(highSource);
        this.med = new Image(medSource);
        if (task.getIsDone()) {
            this.labelBullet.setFill(new ImagePattern(this.tick));
        } else if (task.getPriority().value.equals("high")) {
            this.labelBullet.setFill(new ImagePattern(this.high));
        } else if (task.getPriority().value.equals("med")) {
            this.labelBullet.setFill(new ImagePattern(this.med));
        } else if (task.getPriority().value.equals("low")) {
            this.labelBullet.setOpacity(0);
        }
    }

    private void setTimeColor(ReadOnlyTask task) {
        if (task.hasEndTime() && isOverdue(task.getDeadline().value)) {
            setOverdue(task);
        } else if (task.hasEndTime() && isDueSoon(task.getDeadline().value)) {
            setDueSoon(task);
        } else {
            setNormalColor(task);
        }
    }
    private void setOverdue(ReadOnlyTask task) {
        this.startTime.setStyle("-fx-text-fill:red");
        this.deadline.setStyle("-fx-text-fill:red");
        this.to.setStyle("-fx-text-fill:red");
    }

    private void setNormalColor(ReadOnlyTask task) {
        this.startTime.setStyle("-fx-text-fill:white");
        this.deadline.setStyle("-fx-text-fill:white");
        this.to.setStyle("-fx-text-fill:white");
    }

    private void setDueSoon(ReadOnlyTask task) {
        this.startTime.setStyle("-fx-text-fill: #FFFF66");
        this.deadline.setStyle("-fx-text-fill:  #FFFF66");
        this.to.setStyle("-fx-text-fill:#FFFF66");
    }
    private void setStartTimeEndTime(ReadOnlyTask task) {
        if (task.hasStartTime() && task.hasEndTime()) {
            this.startTime.setVisible(true);
            this.startTime.setText(task.getStartTime().value);
            this.to.setVisible(true);
            this.deadline.setVisible(true);
            this.deadline.setText(task.getDeadline().value);
        } else if (task.hasStartTime()) {
            this.startTime.setVisible(true);
            this.startTime.setText(task.getStartTime().value);
            this.to.setVisible(false);
            this.deadline.setVisible(false);
        } else if (task.hasEndTime()) {
            this.startTime.setVisible(false);
            this.to.setVisible(false);
            this.deadline.setVisible(true);
            this.deadline.setText(task.getDeadline().value);
        } else {
            this.startTime.setVisible(false);
            this.to.setVisible(false);
            this.deadline.setVisible(false);
        }
    }
}
    //@@author
