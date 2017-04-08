package seedu.taskboss.commons.events.ui;

import seedu.taskboss.commons.events.BaseEvent;
import seedu.taskboss.model.category.Category;

//@@author A0143157J
/**
 * Indicates a request to jump to the list of categories
 */
public class JumpToCategoryListEvent extends BaseEvent {
    public final Category category;

    public JumpToCategoryListEvent(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
