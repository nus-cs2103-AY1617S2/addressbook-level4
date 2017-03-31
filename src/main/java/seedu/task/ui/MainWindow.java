package seedu.task.ui;

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
import seedu.task.commons.core.Config;
import seedu.task.commons.core.GuiSettings;
import seedu.task.commons.events.ui.ExitAppRequestEvent;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.logic.Logic;
import seedu.task.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/capital-K icon.png";
    private static final String FXML = "MainWindowDefault.fxml";
    protected static final String FXML_Light = "MainWindowLight.fxml";
    protected static final String FXML_Dark = "MainWindowDark.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private TaskListPanel taskListPanel;
    private Config config;
    private CommandBox commandBox;
    private UserPrefs userPrefs;
    private Scroll scroll;

    @FXML
    private AnchorPane browserPlaceholder;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem helpFormatMenuItem;

    @FXML
    private MenuItem quickAddMenuItem;

    @FXML
    private MenuItem quickUndoMenuItem;

    @FXML
    private MenuItem quickDoneMenuItem;

    @FXML
    private MenuItem quickEditMenuItem;

    @FXML
    private MenuItem quickSelectMenuItem;

    @FXML
    private MenuItem quickSaveMenuItem;

    @FXML
    private MenuItem quickLoadMenuItem;

    @FXML
    private MenuItem quickScrollDownMenuItem;

    @FXML
    private MenuItem quickScrollUpMenuItem;

    @FXML
    private AnchorPane taskListPanelPlaceholder;

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
        this.userPrefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);

        setAccelerators();
    }
  //@@author A0142487Y-reused
    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, String fxml) {
        super(fxml);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.userPrefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);

        setAccelerators();
        setScroll();
    }

    //@@author
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(helpFormatMenuItem, KeyCombination.valueOf("F3"));
        setAccelerator(quickAddMenuItem, KeyCombination.valueOf("Ctrl+Alt+A"));
        setAccelerator(quickUndoMenuItem, KeyCombination.valueOf("Ctrl+Alt+Z"));
        setAccelerator(quickDoneMenuItem, KeyCombination.valueOf("Ctrl+Alt+D"));
        setAccelerator(quickEditMenuItem, KeyCombination.valueOf("Ctrl+Alt+E"));
        setAccelerator(quickSelectMenuItem, KeyCombination.valueOf("Ctrl+Alt+S"));
        setAccelerator(quickSaveMenuItem, KeyCombination.valueOf("Ctrl+S"));
        setAccelerator(quickLoadMenuItem, KeyCombination.valueOf("Ctrl+Alt+L"));
        setAccelerator(quickScrollDownMenuItem, KeyCombination.valueOf("Shift+DOWN"));
        setAccelerator(quickScrollUpMenuItem, KeyCombination.valueOf("SHIFT+UP"));
    }

    private void setScroll() {
        scroll = new Scroll();
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
  //@@author A0142487Y
    void fillInnerParts() {
        switch (this.userPrefs.getTheme()) {
        case Dark:
            taskListPanel = new TaskListPanel(getTaskListPlaceholder(), logic.getFilteredTaskList(),
                    TaskListPanel.FXML_Dark, this.userPrefs.getTheme());
            new ResultDisplay(getResultDisplayPlaceholder(), ResultDisplay.FXML_Dark);
            commandBox = new CommandBox(getCommandBoxPlaceholder(), logic, CommandBox.FXML_Dark);
            break;
        case Light:
            taskListPanel = new TaskListPanel(getTaskListPlaceholder(), logic.getFilteredTaskList(),
                    TaskListPanel.FXML_Light, this.userPrefs.getTheme());
            new ResultDisplay(getResultDisplayPlaceholder(), ResultDisplay.FXML_Light);
            commandBox = new CommandBox(getCommandBoxPlaceholder(), logic, CommandBox.FXML_Light);
            break;
        default:
            taskListPanel = new TaskListPanel(getTaskListPlaceholder(), logic.getFilteredTaskList());
            new ResultDisplay(getResultDisplayPlaceholder());
            commandBox = new CommandBox(getCommandBoxPlaceholder(), logic);
            break;
        }
        browserPanel = new BrowserPanel(browserPlaceholder);
        new StatusBarFooter(getStatusbarPlaceholder(), config.getTaskManagerFilePath());
    }

    //@@author
    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    private AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
    }

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

    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    @FXML
    public void handleHelpFormat() {
        HelpFormatWindow helpFormatWindow = new HelpFormatWindow();
        helpFormatWindow.show();
    }

    @FXML
    public void handleAdd() {
        commandBox.type("add ");
    }

    @FXML
    public void handleUndo() {
        commandBox.type("undo");
    }

    public void handleDone() {
        commandBox.type("done ");
    }

    public void handleEdit() {
        commandBox.type("edit ");
    }

    public void handleSelect() {
        commandBox.type("select ");
    }

    public void handleSave() {
        commandBox.type("save");
    }

    public void handleLoad() {
        commandBox.type("load ");
    }

    //@@author A0142939W
    @FXML
    public void handleScrollDown() {
        taskListPanel.scrollDown(scroll);
    }

    @FXML
    public void handleScrollUp() {
        taskListPanel.scrollUp(scroll);
    }
    //@@author

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

    public TaskListPanel getTaskListPanel() {
        return this.taskListPanel;
    }

    public void setFocus() {
        commandBox.requestFocus();
    }

//    void loadTaskPage(ReadOnlyTask task) {
//        browserPanel.loadTaskPage(task);
//    }

    void releaseResources() {
        browserPanel.freeResources();
    }

}
