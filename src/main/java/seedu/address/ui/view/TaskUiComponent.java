package seedu.address.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.task.Task;

/**
 * Created by louis on 21/2/17.
 */
public class TaskUiComponent extends UiComponent {

    private static final String FXML = "TaskView.fxml";

    @FXML
    private Label name;
    @FXML
    private Label id;

    private Task task;
    private int displayedIndex;


    public TaskUiComponent (Pane parent, Task task, int displayedIndex) {
        super(FXML, parent);
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
