package seedu.taskmanager.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.core.GuiSettings;
import seedu.taskmanager.commons.events.ui.ExitAppRequestEvent;
import seedu.taskmanager.commons.util.FxViewUtil;
import seedu.taskmanager.logic.Logic;
import seedu.taskmanager.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/taskmanager.jpg";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 800;
    private static final int MIN_WIDTH = 600;
    public static final String TAB_TO_DO = "To Do";
    public static final String TAB_DONE = "Done";
    public static final int TAB_TO_DO_INDEX = 0;
    public static final int TAB_DONE_INDEX = 1;

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    
    // @@author A0114523U
    private TaskListPanel todayTaskListPanel;
    private TaskListPanel overdueTaskListPanel;
    // @@author

    // @@author A0131278H
    private TaskListPanel toDoTaskListPanel;
    private TaskListPanel doneTaskListPanel;
    // @@author

    private Config config;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    // @@author A0131278H
    @FXML
    private TabPane taskListsTabPane;
    // @@author

    @FXML
    private AnchorPane taskListPanelPlaceholder;

    // @@author A0114523U
    @FXML
    private AnchorPane overdueTaskListPanelPlaceholder;
    private AnchorPane todayTaskListPanelPlaceholder;
    // @@author
    
    // @@author A0131278H
    @FXML
    private AnchorPane toDoTaskListPanelPlaceholder;

    @FXML
    private AnchorPane doneTaskListPanelPlaceholder;
    // @@author

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);

        setAccelerators();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination
     *            the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666 is fixed in later
         * version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea)
         * will consume function-key events. Because CommandBox contains a
         * TextField, and ResultDisplay contains a TextArea, thus some
         * accelerators (e.g F1) will not work when the focus is in them because
         * the key event is consumed by the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and
         * open help window purposely so to support accelerators even when focus
         * is in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    void fillInnerParts() {

//        browserPanel = new BrowserPanel(browserPlaceholder);
//        taskListPanel = new TaskListPanel(getTaskListPlaceholder(), logic.getFilteredTaskList());
        // @@author A0114523U
        overdueTaskListPanel = new TaskListPanel(getOverdueTaskListPlaceholder(), logic.getFilteredOverdueTaskList());
        todayTaskListPanel = new TaskListPanel(getTodayTaskListPlaceholder(), logic.getFilteredTodayTaskList());
        // @@author

        // @@author A0131278H
        toDoTaskListPanel = new TaskListPanel(getToDoTaskListPlaceholder(), logic.getFilteredToDoTaskList());
        doneTaskListPanel = new TaskListPanel(getDoneTaskListPlaceholder(), logic.getFilteredDoneTaskList());
        // @@author
        new ResultDisplay(getResultDisplayPlaceholder());
        new StatusBarFooter(getStatusbarPlaceholder(), config.getTaskManagerFilePath());
        new CommandBox(getCommandBoxPlaceholder(), logic);
    }

    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    // @@author A0131278H
    private AnchorPane getToDoTaskListPlaceholder() {
        return toDoTaskListPanelPlaceholder;
    }

    private AnchorPane getDoneTaskListPlaceholder() {
        return doneTaskListPanelPlaceholder;
    }

    public TaskListPanel getToDoTaskListPanel() {
        return this.toDoTaskListPanel;
    }

    public TaskListPanel getDoneTaskListPanel() {
        return this.doneTaskListPanel;
    }
    // @@author

    // @@author A0114523U
    private AnchorPane getOverdueTaskListPlaceholder() {
        return overdueTaskListPanelPlaceholder;
    }
    
    private AnchorPane getTodayTaskListPlaceholder() {
    	return todayTaskListPanelPlaceholder;
    }
    
    public TaskListPanel getOverdueTaskListPanel() {
    	return this.overdueTaskListPanel;
    }
    
    public TaskListPanel getTodayTaskListPanel() {
    	return this.todayTaskListPanel;
    }
    // @@author

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     *
     * @param iconSource
     *            e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(), (int) primaryStage.getX(),
                (int) primaryStage.getY());
    }

    // @@author A0131278H

    public void selectTab(int targetIndex) {
        taskListsTabPane.getSelectionModel().select(targetIndex);
    }

    @FXML
    public void onSelectedTabChanged() {
        try {
            String selectedTab = taskListsTabPane.getSelectionModel().getSelectedItem().getText();
            logic.setSelectedTab(selectedTab);
        } catch (NullPointerException npe) {
            // null pointer only invoked at initiation
        }
    }

    public TaskListPanel getTaskListPanel() {
        String selectedTab = taskListsTabPane.getSelectionModel().getSelectedItem().getText();
        switch (selectedTab) {
        case TAB_TO_DO:
            return toDoTaskListPanel;
        case TAB_DONE:
            return doneTaskListPanel;
        default:
            assert false : selectedTab + " is invalid";
            return null;
        }
    }
    // @@author

    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }
}
