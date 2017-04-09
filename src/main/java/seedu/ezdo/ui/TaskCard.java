package seedu.ezdo.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ezdo.logic.parser.DateParser;
import seedu.ezdo.model.todo.ReadOnlyTask;

//@@author A0139177W
/**
 * The task card for the UI.
 */
public class TaskCard extends UiPart<Region> {

    private static final String NUMBERING_FORMAT = ". ";
    private static final String HAS_COMPLETED_LINK = "/images/tick.png";
    private static final String HAS_STARTED_LINK = "/images/wip.png";

    private static final String DEFAULT_PRIORITY_NUMBER = "";
    private static final String DEFAULT_PRIORITY_COLOR = "transparent";

    private static final String HIGH_PRIORITY_NUMBER = "1";
    private static final String HIGH_PRIORITY_COLOR = "red";

    private static final String MEDIUM_PRIORITY_NUMBER = "2";
    private static final String MEDIUM_PRIORITY_COLOR = "orange";

    private static final String LOW_PRIORITY_NUMBER = "3";
    private static final String LOW_PRIORITY_COLOR = "green";

    private static final String FXML = "TaskListCard.fxml";
    private static final String CSS_BACKGROUND_COLOR_PROPERTY = "-fx-background-color: ";

    private static final String CSS_STARTDATE_PAST_CURRENT_DATE_COLOR =
            "-fx-text-fill: darkgreen; -fx-font-weight: bold";

    private static final String CSS_OVERDUE_COLOR =
            "-fx-text-fill: red; -fx-font-weight: bold";

    private static final String CSS_ABOUT_TO_DUE_COLOR =
            "-fx-text-fill: orangered; -fx-font-weight: bold";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(DateParser.USER_DATE_OUTPUT_FORMAT);

    public static final HashMap<String, String> PRIORITY_COLOR_HASHMAP = new HashMap<>();

    private String priorityInString;


    @FXML
    private HBox cardPane;
    @FXML
    private AnchorPane priorityColor;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label status;
    @FXML
    private Label priority;
    @FXML
    private Label startDate;
    @FXML
    private Label dueDate;
    @FXML
    private Label recur;
    @FXML
    private FlowPane tags;

    /**
     * Creates a TaskCard object with all fields to be shown in UI.
     * @param task              The task to be updated.
     * @param displayedIndex    The index of the task.
     */
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        setPriorityColorHashMap();
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + NUMBERING_FORMAT);
        setPriority(task);
        setStatus(task);
        setStartDate(task);
        setDueDate(task);
        recur.setText(task.getRecur().value);
        setStatus(task);
        initTags(task);
    }

    // ========================= STARTDATE ============================ //

    /**
     * Sets the text of start date.
     * Sets the color of start date when the date has commenced.
     * @param task      The task to be updated.
     */
    private void setStartDate(ReadOnlyTask task) {
        Date currentDate = new Date();  // current date and time
        startDate.setText(task.getStartDate().value);
        setStartDateColor(currentDate, CSS_STARTDATE_PAST_CURRENT_DATE_COLOR);
    }

    /**
     * Sets the color of the start date when it has commenced.
     * @param dateReference     The date to be referenced.
     * @param cssColor          The CSS formatting to be used.
     * @throws ParseException   If the start date is optional and cannot be parsed
     *                          as Date object.
     */
    private void setStartDateColor(Date dateReference, String cssColor) {
        try {
            if (dateReference.after(DATE_FORMAT.parse(startDate.getText()))) {
                startDate.setStyle(cssColor);
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    // ========================= DUEDATE ============================ //

    /**
     * Sets the text of the due date.
     * Sets the color of due date when it is overdue and when it's due in 7 days time.
     * @param task              The task to be updated.
     * @throws ParseException   If the due date is optional and cannot be parsed
     *                          as Date object.
     */
    private void setDueDate(ReadOnlyTask task) {
        Date currentDate = new Date();
        Date dateSevenDaysInAdvance = createDateSevenDaysInAdvance();

        dueDate.setText(task.getDueDate().value);

        setAboutToDueDateColor(dateSevenDaysInAdvance, CSS_ABOUT_TO_DUE_COLOR);
        setDueDateColor(currentDate, CSS_OVERDUE_COLOR);
    }

    /**
     * Removes the color of the start date when it's due in 7 days time.
     * Sets the color of the due date when it's due in 7 days time.
     * @param dateReference     The date to be referenced.
     * @param cssColor          The CSS formatting to be used.
     * @throws ParseException   If the due date is optional and cannot be parsed
     *                          as Date object.
     */
    private void setAboutToDueDateColor(Date dateReference, String cssColor) {
        try {
            if (dateReference.after(DATE_FORMAT.parse(dueDate.getText()))) {
                startDate.setStyle(null);
                dueDate.setStyle(cssColor);
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    /**
     * Sets the color of the start date when the due date is overdue.
     * Sets the color of the due date when the due date is overdue.
     * @param dateReference     The date to be referenced.
     * @param cssColor          The CSS formatting to be used.
     * @throws ParseException   If the due date is optional and cannot be parsed
     *                          as Date object.
     */
    private void setDueDateColor(Date dateReference, String cssColor) {
        try {
            if (dateReference.after(DATE_FORMAT.parse(dueDate.getText()))) {
                startDate.setStyle(cssColor);
                dueDate.setStyle(cssColor);
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    /** Returns the date seven days in advance of the current date and time. */
    private Date createDateSevenDaysInAdvance() {
        int weekIncrement = 7;
        Calendar cal = Calendar.getInstance();  // get current date and time
        cal.add(Calendar.DATE, weekIncrement);  // update to seven days ago
        return cal.getTime();
    }

    // ========================= PRIORITY ============================ //

    /** Sets priorityInString as a local variable. **/
    private void setPriorityInString(ReadOnlyTask task) {
        String priorityValue = task.getPriority().value;
        this.priorityInString = priorityValue;
    }

    /** Initialises the colors and priority numbers in PRIORITY_COLOR_HASHMAP. **/
    private void setPriorityColorHashMap() {
        PRIORITY_COLOR_HASHMAP.put(DEFAULT_PRIORITY_NUMBER, DEFAULT_PRIORITY_COLOR);
        PRIORITY_COLOR_HASHMAP.put(LOW_PRIORITY_NUMBER, LOW_PRIORITY_COLOR);
        PRIORITY_COLOR_HASHMAP.put(MEDIUM_PRIORITY_NUMBER, MEDIUM_PRIORITY_COLOR);
        PRIORITY_COLOR_HASHMAP.put(HIGH_PRIORITY_NUMBER, HIGH_PRIORITY_COLOR);
    }

    /**
     * Sets the priority text and color.
     * @param task      The task to be updated.
     */
    private void setPriority(ReadOnlyTask task) {
        setPriorityInString(task);
        priority.setText(priorityInString); // Invisible in UI (for testing purposes)
        priorityColor.setStyle(CSS_BACKGROUND_COLOR_PROPERTY + PRIORITY_COLOR_HASHMAP.get(priorityInString));
    }

    // ========================= STATUS ============================ //
    /**
     * Sets the image if a task has selected or if the task has completed.
     * @param task      The task to be updated.
     */
    private void setStatus(ReadOnlyTask task) {
        if (task.getStarted()) {
            status.setGraphic(new ImageView(HAS_STARTED_LINK));
        }
        if (task.getDone()) {
            status.setGraphic(new ImageView(HAS_COMPLETED_LINK));
        }
    }

    // ========================= TAGS ============================ //
    /**
     * Sets the tags given a task.
     * @param task      The task to be updated.
     */
    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
//@@author A0139177W
