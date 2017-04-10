package typetask.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import typetask.logic.parser.DateParser;
import typetask.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String PRIORITY = "/images/yellow_exclamation_mark.png";
    public static final String COMPLETED_STYLE_CLASS = "completed";
    public static final String OVERDUE_STYLE_CLASS = "overdue";
    public static final String PENDING_STYLE_CLASS = "pending";

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label date;

    @FXML
    private Label endDate;

    @FXML
    private Label preposition;

    @FXML
    private Pane colourTag;

    @FXML
    private Pane completedColourTag;

    @FXML
    private ImageView priorityMark;

    private Image priority = new Image(PRIORITY);

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        date.setText(task.getDate().value);
        setDates(task);
        setPrepositionForDates(task);
        setStatusForTask(task);
        setImageToIndicatePriority(task);
    }

    //@@author A0139926R
    /**
     * Takes @param task to check if this task is overdue or not
     * Compares using current date
     * Sets color for overdue task and pending task
     */
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
        if (task.getIsCompleted()) {
            setStyleToIndicateCompleted(task);
        }
    }

    //@@author A0139154E
    private void setDates(ReadOnlyTask task) {
        if (task.getEndDate().value.equals("")) {
            endDate.setText("");
        } else {
            endDate.setText(task.getEndDate().value);
        }
    }

    private void setPrepositionForDates(ReadOnlyTask task) {
        boolean dateIsEmpty = task.getDate().value.equals("");
        boolean endDateIsEmpty = task.getEndDate().value.equals("");
        if (dateIsEmpty && !endDateIsEmpty) {
            preposition.setText("BY");
        } else if (!dateIsEmpty && !endDateIsEmpty) {
            preposition.setText("TO");
        } else {
            preposition.setText("-");
        }
    }

    private void setStyleToIndicateOverdue() {
        colourTag.getStyleClass().add(OVERDUE_STYLE_CLASS);
    }

    private void setStyleToIndicatePending() {
        colourTag.getStyleClass().add(PENDING_STYLE_CLASS);
    }

    private void setStyleToIndicateCompleted(ReadOnlyTask task) {
        colourTag.getStyleClass().add(COMPLETED_STYLE_CLASS);
    }

    private void setImageToIndicatePriority(ReadOnlyTask task) {
        if (task.getPriority().value.equals("High")) {
            priorityMark.setImage(priority);
        }
    }

}
