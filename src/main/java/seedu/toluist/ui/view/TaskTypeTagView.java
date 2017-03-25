//@@author A0131125Y
package seedu.toluist.ui.view;

import seedu.toluist.commons.util.FxViewUtil;

/**
 * View to display the task type
 */
public class TaskTypeTagView extends TagView {
    private static final String STYLE_CLASS_EVENT = "event-tag";
    private static final String STYLE_CLASS_TASK = "task-tag";
    private static final String DISPLAY_NAME_EVENT = "Event";
    private static final String DISPLAY_NAME_TASK = "Task";

    final boolean isTask;

    public TaskTypeTagView(boolean isTask) {
        super(isTask ? DISPLAY_NAME_TASK : DISPLAY_NAME_EVENT);
        this.isTask = isTask;
    }

    @Override
    protected void viewDidMount() {
        super.viewDidMount();
        if (isTask) {
            FxViewUtil.addStyleClass(tagLabel, STYLE_CLASS_TASK);
        } else {
            FxViewUtil.addStyleClass(tagLabel, STYLE_CLASS_EVENT);
        }
    }
}
