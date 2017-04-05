//@@author A0148037E
package seedu.geekeep.commons.events.model;

import seedu.geekeep.commons.core.TaskCategory;
import seedu.geekeep.commons.events.BaseEvent;

/** Indicates it is time to switch the tabs in mainwindow*/
public class SwitchTaskCategoryEvent extends BaseEvent {
    public TaskCategory category;

    public SwitchTaskCategoryEvent(TaskCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
