package typetask.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import typetask.logic.parser.DateParser;
import typetask.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String PRIORITY = "/images/yellow_exclamation_mark.png";
    public static final String OVERDUE_STYLE_CLASS = "overdue";
    public static final String PENDING_STYLE_CLASS = "pending";
    public static final String PRIORITY_STYLE_CLASS = "priority";

    @FXML
    private HBox cardPane;
    @FXML
    private HBox taskNamePane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;
    @FXML
    private Pane colourTag;
    @FXML
    private ImageView priorityMark;

    private LocalDateTime now = LocalDateTime.now();
    private LocalDate nowDate = now.toLocalDate();
    private String inputPattern = "dd/MM/yyyy";
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern(inputPattern);
    // NOTE: only instantiated for non-floating task
    private LocalDate parsedDate;
    private boolean parsedDateFlag = false;
    private Image priority = new Image(PRIORITY);

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        if (task.getDate().value.equals("")) {
            date.setText("-           " + "          to");
        } else {
            date.setText(task.getDate().value + "                to         ");
        }

        if (task.getEndDate().value.equals("")) {
            endDate.setText("-");
        } else {
            endDate.setText(task.getEndDate().value);
        }
        setStatusForEventTask(task);
        setStatusForDeadlineTask(task);
        setColourCode();
//        setImageToIndicatePriority();
    }
    //@@author A0139926R
    //Checks event task status. Uses endDate to check
    private void setStatusForEventTask(ReadOnlyTask task) {
        if (!task.getEndDate().value.equals("")) {
            List<Date> dates = DateParser.parse(task.getEndDate().value);
            Date taskDeadline = dates.get(0);
            Calendar calendar = Calendar.getInstance();
            Date nowDate = calendar.getTime();
            if (nowDate.after(taskDeadline)) {
                setStyleToIndicateOverdue();
            } else {
                setStyleToIndicatePending();
            }
        }
    }
    //@@author A0139926R
    //Checks deadline task status. Uses date to check
    private void setStatusForDeadlineTask(ReadOnlyTask task) {
        if (!task.getDate().value.equals("")) {
            List<Date> dates = DateParser.parse(task.getDate().value);
            Date taskDeadline = dates.get(0);
            Calendar calendar = Calendar.getInstance();
            Date nowDate = calendar.getTime();
            if (nowDate.after(taskDeadline)) {
                setStyleToIndicateOverdue();
            } else {
                setStyleToIndicatePending();
            }
        }
    }
    //@@author A0139154E
    private void setColourCode() {
        if (parsedDateFlag == true) {
            if (parsedDate.isBefore(nowDate)) {
                setStyleToIndicateOverdue();
            } else {
                setStyleToIndicatePending();
            }
        }
    }

    //@@author A0139154E
    private void setStyleToIndicateOverdue() {
        colourTag.getStyleClass().add(OVERDUE_STYLE_CLASS);
    }

    //@@author A0139154E
    private void setStyleToIndicatePending() {
        colourTag.getStyleClass().add(PENDING_STYLE_CLASS);
    }

    //@@author A0139154E
    private void setImageToIndicatePriority() {
        priorityMark.setImage(priority);
    }

}
