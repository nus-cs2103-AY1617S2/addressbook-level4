//@@author A0131125Y
package seedu.toluist.ui.view;

import seedu.toluist.commons.util.FxViewUtil;

/**
 * View to display the task type
 */
public class TaskTypeTagView extends TagView {
    private static final String EVENT_STYLE_CLASS = "event-tag";
    private static final String TASK_STYLE_CLASS = "task-tag";
    private static final String EVENT_DISPLAY_NAME = "Event";
    private static final String TASK_DISPLAY_NAME = "Task";

    final boolean isTask;

    public TaskTypeTagView(boolean isTask) {
        super(isTask ? TASK_DISPLAY_NAME : EVENT_DISPLAY_NAME);
        this.isTask = isTask;
    }

    @Override
    protected void viewDidMount() {
        super.viewDidMount();
        if (isTask) {
            FxViewUtil.addStyleClass(tagLabel, TASK_STYLE_CLASS);
        } else {
            FxViewUtil.addStyleClass(tagLabel, EVENT_STYLE_CLASS);
        }
    }
}
