package typetask.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import typetask.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    public static final String OVERDUE_STYLE_CLASS = "overdue";
    public static final String PENDING_STYLE_CLASS = "pending";
    public static final String PRIORITY_STYLE_CLASS = "priority";

    @FXML
    private HBox cardPane;
    @FXML
    private HBox taskNamePane;
    @FXML
    private HBox datePane;
    @FXML
    private HBox timePane;
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
    private LocalDateTime now = LocalDateTime.now();
    private LocalDate nowDate = now.toLocalDate();
    private String inputPattern = "dd/MM/yyyy";
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern(inputPattern);
    // NOTE: only instantiated for non-floating task
    private LocalDate parsedDate;
    private boolean parsedDateFlag = false;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        date.setText(task.getDate().value);
        time.setText(task.getTime().value);
        if (!task.getDate().value.equals("")) {
        	parsedDateFlag = true;
        	parsedDate = LocalDate.parse(task.getDate().value, dtf);
        }
//        time.setText(parsedDate.toString());
        setColourCode();
        //add endDate.setText(...);

    }

    //@@author A0139154E
    private void setColourCode() {
//        if (task.getName().fullName.equals("lunch")) {
//        	setStyleToIndicatePriority();
//        }
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
    private void setStyleToIndicatePriority() {
        colourTag.getStyleClass().add(PRIORITY_STYLE_CLASS);
    }

}
