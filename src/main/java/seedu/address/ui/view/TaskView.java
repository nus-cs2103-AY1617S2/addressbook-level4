package seedu.address.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

/**
 * Created by louis on 21/2/17.
 */
public class TaskView extends UiPart<Region> {

    private static final String FXML = "TaskView.fxml";

    @FXML
    private Label name;
    @FXML
    private Label id;


    public TaskView(Task task, int displayedIndex) {
        super(FXML);
        name.setText(task.description);
        id.setText(displayedIndex + ". ");
    }
}
