import javafx.scene.layout.Region;
import seedu.taskboss.model.category.Category;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    //@@author A0143157J-unused
    /**
     * Loads tasks from the selected category.
     *
     * Unused reason:
     * Refer to reason under CategoryListPanelViewingChangedEvent.
     */
    public void loadCategorySelection(Category category) {
        logic.updateFilteredTaskListByCategory(category);
    }
}
