import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.taskboss.commons.core.Config;
import seedu.taskboss.commons.core.GuiSettings;
import seedu.taskboss.commons.events.ui.ExitAppRequestEvent;
import seedu.taskboss.commons.util.FxViewUtil;
import seedu.taskboss.logic.Logic;
import seedu.taskboss.model.UserPrefs;
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
