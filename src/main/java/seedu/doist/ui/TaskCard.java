package seedu.doist.ui;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.doist.model.task.Priority;
import seedu.doist.model.task.ReadOnlyTask;

//@@author A0140887W
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String START_TIME_TEXT = "Start: ";
    private static final String END_TIME_TEXT = "End: ";
    private static final String BY_TIME_TEXT = "By: ";
    public static final String NORMAL_STYLE_CLASS = "normal";
    public static final String OVERDUE_STYLE_CLASS = "overdue";
    public static final String FINISHED_STYLE_CLASS = "finished";

    @FXML
    private BorderPane cardPane;
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
    @FXML
    private ImageView star1;
    @FXML
    private ImageView star2;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        setStyleToNormal();
        desc.setText(task.getDescription().desc);
        id.setText(displayedIndex + ". ");
        priority.setText(task.getPriority().toString());

        // Times
        if (task.getDates().isDeadline()) {
            startTime.setText(BY_TIME_TEXT + prettyDate(task.getDates().getStartDate()));
            endTime.setText("");
        } else if (task.getDates().isEvent()) {
            startTime.setText(START_TIME_TEXT + prettyDate(task.getDates().getStartDate()));
            endTime.setText(END_TIME_TEXT + prettyDate(task.getDates().getEndDate()));
        } else {
            // floating task
            startTime.setText("");
            endTime.setText("");
        }
        if (task.getDates().isPast()) {
            setStyleToOverdue();
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

        //Importance
        if (task.getPriority().getPriorityLevel().equals(Priority.PriorityLevel.NORMAL)) {
            // Disable both stars
            star1.setImage(null);
            star2.setImage(null);
        } else if (task.getPriority().getPriorityLevel().equals(Priority.PriorityLevel.IMPORTANT)) {
            // Disable one star
            star2.setImage(null);
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

    //@@author A0147620L
    private String prettyDate (Date date) {
        StringBuilder prettydate = new StringBuilder();
        prettydate.append(prettyMonth (date.getMonth() + 1));
        prettydate.append(" " + date.getDate() + ", ");
        prettydate.append((date.getYear() + 1900) + " at ");
        prettydate.append(prettyTime(date.getHours(), date.getMinutes()));
        return prettydate.toString();
    }

    private String prettyMonth (int month) {
        switch (month) {
        case 1 : return "January";
        case 2 : return "February";
        case 3 : return "March";
        case 4 : return "April";
        case 5 : return "May";
        case 6 : return "June";
        case 7 : return "July";
        case 8 : return "August";
        case 9 : return "September";
        case 10 : return "October";
        case 11 : return "November";
        case 12 : return "December";
        default : return null;
        }
    }

    private String prettyTime (int hours, int minutes) {
        String suffix = (hours <= 12) ? "am" : "pm";
        String hour = (hours <= 12) ? Integer.toString(hours) : Integer.toString(hours - 12);
        String minute = (minutes < 10) ? "0" + Integer.toString(minutes) : Integer.toString(minutes);
        return hour + ":" + minute + suffix;
    }
    //@@author
}
