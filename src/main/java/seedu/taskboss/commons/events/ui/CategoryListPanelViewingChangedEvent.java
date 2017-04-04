package seedu.taskboss.commons.events.ui;

import seedu.taskboss.commons.events.BaseEvent;
import seedu.taskboss.model.category.Category;

public class CategoryListPanelViewingChangedEvent extends BaseEvent {

    private final Category newViewing;

    public CategoryListPanelViewingChangedEvent(Category newViewing) {
        this.newViewing = newViewing;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Category getNewViewing() {
        return newViewing;
    }
}
