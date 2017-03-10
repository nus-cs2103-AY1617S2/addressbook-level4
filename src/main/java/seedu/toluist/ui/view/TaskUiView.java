package seedu.toluist.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.toluist.commons.util.DateFormatterUtil;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.model.Task;

import java.time.LocalDateTime;

/**
 * View to display task row
 */
public class TaskUiView extends UiView {

    private static final String FXML = "TaskView.fxml";

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;

    private Task task;
    private int displayedIndex;


    public TaskUiView (Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        this.displayedIndex = displayedIndex;
    }

    @Override
    protected void viewDidMount() {
        name.setText(task.description);
        id.setText(displayedIndex + ". ");
        setDate();
        FxViewUtil.makeFullWidth(getRoot());
    }

    private void setDate() {
        if (task.isTask()) {
            date.setText(DateFormatterUtil.formatTaskDeadline(task.getStartDateTime()));
        } else if (task.isEvent()) {
            date.setText(DateFormatterUtil.formatEventRange(task.getStartDateTime(), task.getEndDateTime()));
        }
    }
}
