package seedu.toluist.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.model.Task;

/**
 * View to display task row
 */
public class TaskUiView extends UiView {

    private static final String FXML = "TaskView.fxml";

    @FXML
    private Label name;
    @FXML
    private Label id;

    private Task task;
    private int displayedIndex;


    public TaskUiView (Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        this.displayedIndex = displayedIndex;
    }

    @Override
    protected void viewDidMount() {
        String fullDescription = task.description;
        if (task.startDateTime != null) {
            fullDescription += "\nStart: " + DateTimeUtil.toString(task.startDateTime);
        }
        if (task.endDateTime != null) {
            fullDescription += "\nEnd: " + DateTimeUtil.toString(task.endDateTime);
        }
        name.setText(fullDescription);
        id.setText(displayedIndex + ". ");
        FxViewUtil.makeFullWidth(getRoot());
    }
}
