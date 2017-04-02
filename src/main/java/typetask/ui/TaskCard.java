package typetask.ui;

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
    private Label preposition;  
    @FXML
    private Pane colourTag;
    @FXML
    private ImageView priorityMark;

    // NOTE: only instantiated for non-floating task
    private Image priority = new Image(PRIORITY);
    private ReadOnlyTask task;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.task = task;
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        date.setText(task.getDate().value);

        if (task.getEndDate().value.equals("")) {
            endDate.setText("-");
        } else {
            endDate.setText(task.getEndDate().value);
        }
        setPrepositionForDates();
        setStatusForTask(task);
//        setImageToIndicatePriority();
    }

    //@@author A0139926R
    //Checks event task status. Uses endDate to check
    private void setStatusForTask(ReadOnlyTask task) {
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



    //@@author A0139154E
    private void setPrepositionForDates() {
        boolean dateIsEmpty = task.getDate().value.equals("");
        boolean endDateIsEmpty = task.getEndDate().value.equals("");
        if (dateIsEmpty && !endDateIsEmpty) {
            preposition.setText("BY");
        } else if (!dateIsEmpty && !endDateIsEmpty) {
            preposition.setText("TO");
        } else {
            preposition.setText("");
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
