package typetask.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import typetask.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String PRIORITY = "/images/yellow_exclamation_mark.png";
    private static final String DONE = "/images/green_tick.png";
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
    private ImageView priorityOrDone;

    private LocalDateTime now = LocalDateTime.now();
    private LocalDate nowDate = now.toLocalDate();
    private String inputPattern = "dd/MM/yyyy";
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern(inputPattern);
    // NOTE: only instantiated for non-floating task
    private LocalDate parsedDate;
    private boolean completed = false;
    private boolean parsedDateFlag = false;
    private Image priority = new Image(PRIORITY);
    private Image done = new Image(DONE);

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        date.setText(task.getDate().value);
        endDate.setText(task.getEndDate().value);
        completed = task.getIsCompleted();
//        if (!task.getDate().value.equals("")) {
//            parsedDateFlag = true;
//            parsedDate = LocalDate.parse(task.getDate().value, dtf);
//        }
        setColourCode();
        setImagestoIndicatePriorityOrComplete();
        //add endDate.setText(...);
        //add setImages...
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
    private void setImagestoIndicatePriorityOrComplete() {
        if (completed == true) {
            setImageToIndicateCompleted();
        }
    }

    //@@author A0139154E
    private void setImageToIndicatePriority() {
        priorityOrDone.setImage(priority);
    }

    //@@author A0139154E
    private void setImageToIndicateCompleted() {
        priorityOrDone.setImage(done);
    }


}
