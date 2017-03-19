package seedu.toluist.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import seedu.toluist.commons.util.AppUtil;
import seedu.toluist.commons.util.DateTimeFormatterUtil;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;

/**
 * View to display task row
 */
public class TaskUiView extends UiView {

    private static final String FXML = "TaskView.fxml";
    private static final String CLOCK_ICON_IMAGE_PATH = "/images/clock.png";
    private static final String COMPLETED_STYLE_CLASS = "completed";

    @FXML
    private FlowPane tagsPane;
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
        boolean isFloatingTask = task.isFloatingTask();
        boolean isTaskWithDeadline = task.isTaskWithDeadline();
        boolean isTask = isFloatingTask || isTaskWithDeadline;
        boolean isEvent = task.isEvent();

        tagsPane.getChildren().clear();

        TaskTypeTagView taskTypeTagView = new TaskTypeTagView(isTask);
        taskTypeTagView.setParent(tagsPane);
        taskTypeTagView.render();

        for (Tag tag : task.getAllTags()) {
            TagView tagView = new TagView(tag.tagName);
            tagView.setParent(tagsPane);
            tagView.render();
        }

        name.setText(task.getDescription());
        id.setText(displayedIndex + ". ");
        if (isTaskWithDeadline) {
            date.setText(DateTimeFormatterUtil.formatTaskDeadline(task.getEndDateTime()));
        } else if (isEvent) {
            date.setText(DateTimeFormatterUtil.formatEventRange(task.getStartDateTime(), task.getEndDateTime()));
        }
        if (isTaskWithDeadline || task.isEvent()) {
            clockIcon.setImage(AppUtil.getImage(CLOCK_ICON_IMAGE_PATH));
        }
        if (task.isCompleted()) {
            FxViewUtil.addStyleClass(name, COMPLETED_STYLE_CLASS);
        }
    }
}
