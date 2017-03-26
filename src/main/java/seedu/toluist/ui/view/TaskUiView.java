//@@author A0127545A
package seedu.toluist.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private static final String IMAGE_PATH_CLOCK_ICON = "/images/clock.png";
    private static final String IMAGE_PATH_OVERDUE_ICON = "/images/warning.png";
    private static final String IMAGE_PATH_HIGH_PRIORITY_ICON = "/images/star.png";
    private static final String IMAGE_PATH_RECURRING_ICON = "/images/recurring_icon.png";
    private static final String STYLE_CLASS_COMPLETED = "completed";
    private static final String STYLE_CLASS_OVERDUE = "overdue";
    private static final double STATUS_BOX_SPACING_VALUE = 10.0;

    @FXML
    private Pane taskPane;
    @FXML
    private FlowPane tagsPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label recurringDate;
    @FXML
    private HBox statusBox;
    @FXML
    private ImageView clockIcon;
    @FXML
    private ImageView recurringIcon;

    private Task task;
    private int displayedIndex;


    public TaskUiView (Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        this.displayedIndex = displayedIndex;
    }

    @Override
    protected void viewDidMount() {
        initializeView();
        handleTaskType();
        handleTaskWithTags();
        handleOverdueTask();
        handleHighPriorityTask();
        handleTaskDescriptionAndId();
        handleTaskWithDates(task.isTaskWithDeadline() || task.isEvent());
        handleRecurringTask(task.isRecurring());
        handleCompletedTask();
    }

    private void initializeView() {
        tagsPane.getChildren().clear();
        statusBox.getChildren().clear();
        statusBox.setSpacing(STATUS_BOX_SPACING_VALUE);
    }

    private void handleTaskType() {
        TaskTypeTagView taskTypeTagView = new TaskTypeTagView(task.isFloatingTask() || task.isTaskWithDeadline());
        taskTypeTagView.setParent(tagsPane);
        taskTypeTagView.render();
    }

    private void handleTaskWithTags() {
        for (Tag tag : task.getAllTags()) {
            renderTag(tag);
        }
    }

    private void renderTag(Tag tag) {
        TagView tagView = new TagView(tag.tagName);
        tagView.setParent(tagsPane);
        tagView.render();
    }

    private void handleOverdueTask() {
        if (task.isOverdue()) {
            TaskStatusView statusView = new TaskStatusView(AppUtil.getImage(IMAGE_PATH_OVERDUE_ICON));
            statusView.setParent(statusBox);
            statusView.render();
            FxViewUtil.addStyleClass(taskPane, STYLE_CLASS_OVERDUE);
        }
    }

    private void handleHighPriorityTask() {
        if (task.isHighPriority()) {
            TaskStatusView highPriorityView = new TaskStatusView(AppUtil.getImage(IMAGE_PATH_HIGH_PRIORITY_ICON));
            highPriorityView.setParent(statusBox);
            highPriorityView.render();
        }
    }

    private void handleTaskDescriptionAndId() {
        name.setText(task.getDescription());
        id.setText(displayedIndex + ". ");
    }

    private void handleTaskWithDates(boolean isShown) {
        if (isShown) {
            clockIcon.setImage(AppUtil.getImage(IMAGE_PATH_CLOCK_ICON));
            if (task.isTaskWithDeadline()) {
                date.setText(DateTimeFormatterUtil.formatTaskDeadline(task.getEndDateTime()));
            } else if (task.isEvent()) {
                date.setText(DateTimeFormatterUtil.formatEventRange(task.getStartDateTime(), task.getEndDateTime()));
            }
        } else {
            hideDateBox();
            hideClockIcon();
        }
    }

    private void hideDateBox() {
        date.setVisible(false);
        date.setManaged(false);
    }

    private void hideClockIcon() {
        clockIcon.setVisible(false);
        clockIcon.setManaged(false);
    }

    private void handleRecurringTask(boolean isShown) {
        if (isShown) {
            recurringIcon.setImage(AppUtil.getImage(IMAGE_PATH_RECURRING_ICON));
            if (task.isFloatingTask()) {
                recurringDate.setText(
                        DateTimeFormatterUtil.formatRecurringFloatingTask(
                        task.getRecurringEndDateTime(),
                        task.getRecurringFrequency()));
            } else if (task.isTaskWithDeadline()) {
                recurringDate.setText(
                        DateTimeFormatterUtil.formatRecurringTaskDeadline(
                        task.getEndDateTime(),
                        task.getRecurringEndDateTime(),
                        task.getRecurringFrequency()));
            } else if (task.isEvent()) {
                recurringDate.setText(
                        DateTimeFormatterUtil.formatRecurringEvent(
                        task.getStartDateTime(),
                        task.getEndDateTime(),
                        task.getRecurringEndDateTime(),
                        task.getRecurringFrequency()));
            }
        } else {
            hideRecurringIcon();
            hideRecurringDateBox();
        }
    }

    private void hideRecurringIcon() {
        recurringIcon.setVisible(false);
        recurringIcon.setManaged(false);
    }

    private void hideRecurringDateBox() {
        recurringIcon.setVisible(false);
        recurringIcon.setManaged(false);
    }

    private void handleCompletedTask() {
        if (task.isCompleted()) {
            FxViewUtil.addStyleClass(taskPane, STYLE_CLASS_COMPLETED);
        }
    }
}
