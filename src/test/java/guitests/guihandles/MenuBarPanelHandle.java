package guitests.guihandles;

import guitests.GuiRobot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.taskit.TestApp;
import seedu.taskit.model.task.ReadOnlyTask;

//@@author A0141872E
/**
 * Provides a handle for the panel containing the menubar
 */
public class MenuBarPanelHandle extends GuiHandle {

    public static final String MENU_HOME = "Home";
    public static final String MENU_TODAY_TASK = "Today";
    public static final String MENU_OVERDUE_TASK = "Overdue";
    public static final String MENU_FLOATING_TASK = "Simple Tasks";
    public static final String MENU_EVENT_TASK = "Event";
    public static final String MENU_DEADLINE_TASK = "Deadline";

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#menuBarCardPane";

    private static final String MENU_BAR_VIEW_ID = "#menuBarView";

    private final ObservableList<String> menuBarItems = FXCollections.observableArrayList(MENU_HOME, MENU_TODAY_TASK,
            MENU_OVERDUE_TASK, MENU_FLOATING_TASK, MENU_EVENT_TASK, MENU_DEADLINE_TASK);

    public MenuBarPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }


    public ListView<ReadOnlyTask> getListView() {
        return getNode(MENU_BAR_VIEW_ID);
    }

    /**
     * Navigates the menubar view to display relevant task.
     */
    public void navigateTo(String menuBarItem) {
        int index = menuBarItems.indexOf(menuBarItem);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(550);
    }
}
