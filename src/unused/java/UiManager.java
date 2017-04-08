import com.google.common.eventbus.Subscribe;

import seedu.taskboss.commons.core.ComponentManager;
import seedu.taskboss.commons.core.LogsCenter;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {

    //@@author A0143157J-unused
    /**
     * Subscribe to click events on the CategoryListPanel
     * and loads task in the selected category.
     *
     * Unused reason:
     * Refer to reason under CategoryListPanelViewingChangedEvent.
     */
    @Subscribe
    private void handleCategoryListPanelViewingChangedEvent(CategoryListPanelViewingChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.loadCategorySelection(event.getNewViewing());
    }

}
