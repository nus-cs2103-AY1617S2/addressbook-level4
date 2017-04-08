import seedu.taskboss.commons.events.BaseEvent;
import seedu.taskboss.model.category.Category;

//@@author A0143157J-unused
/**
 * Represents a viewing change in the Category List Panel.
 *
 * Unused reason:
 * This is a working implementation of enabling mouse clicks in the CategoryListPanel, to
 * allow users to switch between categories by clicking on the respective category.
 * However, we decided to disable mouse clicks as this implementation of mouse clicks
 * showed different subsequent behaviours after listing categories by typing
 * or clicking on categories, and we thought it would be confusing for our users.
 *
 * Eg. editing a task after clicking on a category, would return to the clicked category;
 * whereas editing a task after listing a category (by typing command), would stay in the listed category.
 *
 * Since our target users are people who favour a type-based experience, we eventually decided to compromise
 * this extra function for usability.
 */
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
