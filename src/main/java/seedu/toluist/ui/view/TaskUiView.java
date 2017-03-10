package seedu.toluist.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import seedu.toluist.commons.util.AppUtil;
import seedu.toluist.commons.util.DateTimeFormatterUtil;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.model.Task;

/**
 * View to display task row
 */
public class TaskUiView extends UiView {

    private static final String FXML = "TaskView.fxml";
    private static final String CLOCK_ICON_IMAGE_PATH = "/images/clock.png";

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private ImageView clockIcon;

    private Task task;
    private int displayedIndex;


    public TaskUiView (Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        this.displayedIndex = displayedIndex;
    }

    @Override
    protected void viewDidMount() {
        FxViewUtil.makeFullWidth(getRoot());
        name.setText(task.getDescription());
        id.setText(displayedIndex + ". ");
        if (task.isTask()) {
            date.setText(DateTimeFormatterUtil.formatTaskDeadline(task.getEndDateTime()));
        } else if (task.isEvent()) {
            date.setText(DateTimeFormatterUtil.formatEventRange(task.getStartDateTime(), task.getEndDateTime()));
        }
        if (task.isTask() || task.isEvent()) {
            clockIcon.setImage(AppUtil.getImage(CLOCK_ICON_IMAGE_PATH));
        }
    }
}
