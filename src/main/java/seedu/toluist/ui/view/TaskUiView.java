package seedu.toluist.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.model.Task;

/**
 * Created by louis on 21/2/17.
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
        name.setText(task.description);
        id.setText(displayedIndex + ". ");
        FxViewUtil.makeFullWidth(getRoot());
    }
}
