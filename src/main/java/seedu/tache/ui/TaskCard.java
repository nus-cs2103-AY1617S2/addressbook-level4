package seedu.tache.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * Card containing details of task.
 */
public class TaskCard extends UiPart<Region> {

    //@@author A0142255M
    private static final String START_DATE_INDICATOR = "Start Date: ";
    private static final String START_TIME_INDICATOR = "Start Time: ";
    private static final String END_DATE_INDICATOR = "End Date: ";
    private static final String END_TIME_INDICATOR = "End Time: ";
    private static final String FXML = "TaskListCard.fxml";

    private final Logger logger = LogsCenter.getLogger(TaskCard.class);
    //@@author

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private FlowPane datesAndTimes;

    @FXML
    private FlowPane tags;

    //@@author A0142255M
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        assert task != null;
        assert displayedIndex > 0;
        id.setText(Integer.toString(displayedIndex) + ". ");
        name.setText(task.getName().toString());
        name.setWrapText(true); // spill over to next line if task name is too long
        initDatesAndTimes(task);
        initTags(task);
    }

    private void initDatesAndTimes(ReadOnlyTask task) {
        if (task.getStartDateTime().isPresent()) {
            DateTime start = task.getStartDateTime().get();
            Label startDateLabel = new Label(START_DATE_INDICATOR + start.getDateOnly());
            startDateLabel.setId("startdate");
            datesAndTimes.getChildren().add(startDateLabel);
            Label startTimeLabel = new Label(START_TIME_INDICATOR + start.getTimeOnly());
            startTimeLabel.setId("starttime");
            datesAndTimes.getChildren().add(startTimeLabel);
            logger.fine("Start date and time added in Labels for " + task.getName().toString());
        }
        if (task.getEndDateTime().isPresent()) {
            DateTime end = task.getEndDateTime().get();
            Label endDateLabel = new Label(END_DATE_INDICATOR + end.getDateOnly());
            endDateLabel.setId("enddate");
            datesAndTimes.getChildren().add(endDateLabel);
            Label endTimeLabel = new Label(END_TIME_INDICATOR + end.getTimeOnly());
            endTimeLabel.setId("endtime");
            datesAndTimes.getChildren().add(endTimeLabel);
            logger.fine("End date and time added in Labels for " + task.getName().toString());
        }
    }
    //@@author

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
