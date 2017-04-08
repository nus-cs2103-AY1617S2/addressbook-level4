package seedu.tache.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tache.MainApp;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * Card containing details of task.
 */
public class TaskCard extends UiPart<Region> {

    //@@author A0142255M
    private static final String INDICATOR_START_DATE = "Start Date: ";
    private static final String INDICATOR_START_TIME = "Start Time: ";
    private static final String INDICATOR_END_DATE = "End Date: ";
    private static final String INDICATOR_END_TIME = "End Time: ";
    private static final String FXML = "TaskListCard.fxml";

    private static final String INDICATOR_COMPLETED = "completed";
    private static final String INDICATOR_OVERDUE = "overdue";
    private static final String INDICATOR_UNCOMPLETED = "uncompleted";

    private final Logger logger = LogsCenter.getLogger(TaskCard.class);

    private String statusOfTask = INDICATOR_UNCOMPLETED;
    private boolean isMasterRecurring = false;
    //@@author

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private ImageView symbol;

    @FXML
    private FlowPane tags;

    @FXML
    private FlowPane datesAndTimes;

    //@@author A0142255M
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        assert task != null;
        assert displayedIndex > 0;
        id.setText(Integer.toString(displayedIndex) + ". ");
        id.setId("id"); // set property id for Label named id
        name.setText(task.getName().toString());
        name.setWrapText(true); // spill over to next line if task name is too long
        setStatusOfTask(task);
        setIsMasterRecurring(task);
        setSymbol();
        setBorderColour();
        initDatesAndTimes(task);
        initTags(task);
    }

    /**
     * Sets task status as completed, overdue or uncompleted.
     * Task status will be used to set border colour and symbol of task card later.
     */
    private void setStatusOfTask(ReadOnlyTask task) {
        assert task != null;
        if (task.getActiveStatus() == false) {
            statusOfTask = INDICATOR_COMPLETED;
        } else if (task.getEndDateTime().isPresent()) {
            DateTime taskDate = task.getEndDateTime().get();
            if (taskDate.hasPassed()) {
                statusOfTask = INDICATOR_OVERDUE;
            }
        }
    }

    /**
     * Checks if this task is the master task, if it is recurring.
     * Sets value of isMasterRecurring as true or false.
     */
    private void setIsMasterRecurring(ReadOnlyTask task) {
        assert task != null;
        isMasterRecurring = task.isMasterRecurring();
    }

    /**
     * Sets border colour of task card according to task status.
     */
    private void setBorderColour() {
        if (statusOfTask.equals(INDICATOR_UNCOMPLETED)) {
            cardPane.setStyle("-fx-border-color: #5f77bd"); // blue
        } else if (statusOfTask.equals(INDICATOR_OVERDUE)) {
            cardPane.setStyle("-fx-border-color: #ef044b"); // red
        } else {
            cardPane.setStyle("-fx-border-color: #14b66c"); // green
        }
    }

    /**
     * Sets symbol of task card according to task status.
     */
    private void setSymbol() {
        if (isMasterRecurring) {
            symbol.setImage(new Image(MainApp.class.getResource("/images/recurring.png").toExternalForm()));
        } else {
            if (statusOfTask.equals(INDICATOR_COMPLETED)) {
                symbol.setImage(new Image(MainApp.class.getResource("/images/tick.png").toExternalForm()));
            } else if (statusOfTask.equals(INDICATOR_OVERDUE)) {
                symbol.setImage(new Image(MainApp.class.getResource("/images/cross.png").toExternalForm()));
            }
        }
        symbol.setId("symbol");
    }

    /**
     * Sets start datetime and end datetime of  task.
     */
    private void initDatesAndTimes(ReadOnlyTask task) {
        assert task != null;
        if (task.getStartDateTime().isPresent()) {
            DateTime start = task.getStartDateTime().get();
            Label startDateLabel = new Label(INDICATOR_START_DATE + start.getDateOnly());
            startDateLabel.setId("startdate");
            datesAndTimes.getChildren().add(startDateLabel);
            Label startTimeLabel = new Label(INDICATOR_START_TIME + start.getTimeOnly());
            startTimeLabel.setId("starttime");
            datesAndTimes.getChildren().add(startTimeLabel);
            logger.fine("Start date and time added in Labels for " + task.getName().toString());
        }
        if (task.getEndDateTime().isPresent()) {
            DateTime end = task.getEndDateTime().get();
            Label endDateLabel = new Label(INDICATOR_END_DATE + end.getDateOnly());
            endDateLabel.setId("enddate");
            datesAndTimes.getChildren().add(endDateLabel);
            Label endTimeLabel = new Label(INDICATOR_END_TIME + end.getTimeOnly());
            endTimeLabel.setId("endtime");
            datesAndTimes.getChildren().add(endTimeLabel);
            logger.fine("End date and time added in Labels for " + task.getName().toString());
        }
    }

    private void initTags(ReadOnlyTask task) {
        assert task != null;
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
