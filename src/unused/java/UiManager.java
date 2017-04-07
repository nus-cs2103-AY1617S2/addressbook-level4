import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.taskboss.MainApp;
import seedu.taskboss.commons.core.ComponentManager;
import seedu.taskboss.commons.core.Config;
import seedu.taskboss.commons.core.LogsCenter;
import seedu.taskboss.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskboss.commons.events.ui.JumpToCategoryListEvent;
import seedu.taskboss.commons.events.ui.JumpToListRequestEvent;
import seedu.taskboss.commons.events.ui.ShowHelpRequestEvent;
import seedu.taskboss.commons.events.ui.TaskPanelViewingChangedEvent;
import seedu.taskboss.commons.util.StringUtil;
import seedu.taskboss.logic.Logic;
import seedu.taskboss.model.UserPrefs;

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
