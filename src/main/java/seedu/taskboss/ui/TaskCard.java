package seedu.taskboss.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskboss.commons.util.AppUtil;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String CSS_CARDPANE_OVERDUE = "-fx-background-color: #FFF7F8; -fx-border-color: #FF0097;"
            + " -fx-border-width: 1 2 1 2;";
    private static final String RECURRENCE_NONE = "NONE";
    private static final String PATH_IMAGE_URGENT = "/images/urgentImage.png";
    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private ImageView priorityLevel;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private Label information;
    @FXML
    private Label recurrence;
    @FXML
    private FlowPane categories;

    //@@author A0143157J
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        initOverdueTaskCss(task);
        initName(task);
        initId(displayedIndex);
        initPriority(task);
        initStartDateTime(task);
        initEndDateTime(task);
        initInformation(task);
        initRecurrence(task);
        initCategories(task);
    }

    /**
     * Initialises CSS properties for a task that is overdue
     */
    private void initOverdueTaskCss(ReadOnlyTask task) {
        Date currDate = new Date();
        if (task.getEndDateTime() == null || task.getEndDateTime().getDate() == null) {
            return;
        }
        Date taskEndDate = task.getEndDateTime().getDate();
        if (isOverdueTask(taskEndDate, currDate, task)) {
            setCardPaneOverdueStyle();
        }
    }

    /**
     * Returns true if a task is overdue by comparing current time with the task's end date time
     */
    private boolean isOverdueTask(Date taskEndDate, Date currDate, ReadOnlyTask task) {
        boolean isOverdue = currDate.after(taskEndDate);
        SimpleDateFormat sdfNoTime = new SimpleDateFormat("MMM dd, yyyy");

        if (!isOverdue) {
            return false;
        } else if (isOverdue) {
            // account for special case (taskEndDate == today) that is marked as true for .after() in the API
            // ie. ed/today (without time) should not be overdue
            if (sdfNoTime.format(taskEndDate).equals(sdfNoTime.format(currDate))
                    && !task.getEndDateTime().isTimeInferred()) {
                return true;
            } else if (!sdfNoTime.format(taskEndDate).equals(sdfNoTime.format(currDate))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Sets CSS style for the cardPane of overdue tasks
     */
    private void setCardPaneOverdueStyle() {
        cardPane.setStyle(CSS_CARDPANE_OVERDUE);
    }

    /**
     * Initialises name
     */
    private void initName(ReadOnlyTask task) {
        name.setText(task.getName().fullName);
    }

    /**
     * Initialises id
     */
    private void initId(int displayedIndex) {
        id.setText(displayedIndex + ". ");
    }

    /**
     * Initialises priority level
     */
    private void initPriority(ReadOnlyTask task) {
        PriorityLevel priority = task.getPriorityLevel();
        Image urgentImage = AppUtil.getImage(PATH_IMAGE_URGENT);
        if (priority.value.equals(PriorityLevel.PRIORITY_HIGH_VALUE)) {
            priorityLevel.setImage(urgentImage);
        } else {
            priorityLevel.setVisible(false);
        }
    }

    /**
     * Initialises start date time
     */
    private void initStartDateTime(ReadOnlyTask task) {
        String startDate = task.getStartDateTime().value;
        if (startDate.isEmpty()) {
            startDateTime.setText("Start: " + startDate);
            startDateTime.setVisible(false);
        } else {
            startDateTime.setText("Start: " + task.getStartDateTime().value);
        }
    }

    /**
     * Initialises end date time
     */
    private void initEndDateTime(ReadOnlyTask task) {
        String endDate = task.getEndDateTime().value;
        if (endDate.isEmpty()) {
            endDateTime.setText("End: " + endDate);
            endDateTime.setVisible(false);
        } else {
            endDateTime.setText("End: " + task.getEndDateTime().value);
        }
    }

    /**
     * Initialises information
     */
    private void initInformation(ReadOnlyTask task) {
        information.setText(task.getInformation().value);
    }

    /**
     * Initialises recurrence
     */
    private void initRecurrence(ReadOnlyTask task) {
        String recurrenceType = task.getRecurrence().toString();
        if (recurrenceType.equals(RECURRENCE_NONE)) {
            recurrence.setText(task.getRecurrence().toString());
            recurrence.setVisible(false);
        } else {
            recurrence.setText(task.getRecurrence().toString());
        }
    }

    /**
     * Initialises categories
     */
    private void initCategories(ReadOnlyTask task) {
        task.getCategories().forEach(category -> categories.getChildren().add(new Label(category.categoryName)));
    }
}
