package seedu.onetwodo.ui;

import java.net.URL;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.onetwodo.MainApp;
import seedu.onetwodo.commons.core.Config;
import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.core.GuiSettings;
import seedu.onetwodo.commons.events.ui.ExitAppRequestEvent;
import seedu.onetwodo.commons.events.ui.NewResultAvailableEvent;
import seedu.onetwodo.commons.util.FxViewUtil;
import seedu.onetwodo.logic.Logic;
import seedu.onetwodo.logic.commands.HelpCommand;
import seedu.onetwodo.logic.commands.ListCommand;
import seedu.onetwodo.logic.commands.RedoCommand;
import seedu.onetwodo.logic.commands.UndoCommand;
import seedu.onetwodo.model.UserPrefs;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.TaskType;

/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final int SCROLL_AMT = 5;
    private static final String LIST_DONE_COMMAND_INPUT = ListCommand.COMMAND_WORD + " done";
    private static final String LIST_UNDONE_COMMAND_INPUT = ListCommand.COMMAND_WORD + " undone";
    private static final String LIST_ALL_COMMAND_INPUT = ListCommand.COMMAND_WORD + " all";

    private static final String FXML = "MainWindow.fxml";
    private static final String FONT_AVENIR = "/fonts/avenir-light.ttf";
    private static final String DONE_STYLESHEET = "view/Strikethrough.css";
    private static final String HELPWINDOW_URL = "/view/help.html";
    private static final String USERGUIDE_URL = "https://cs2103jan2017-f14-b1.github.io/main/UserGuide.html";
    private static final String ICON_IMG = "/images/onetwodo_icon2.png";


    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 650;
    private int deadlineScrollIndex = 0;
    private int eventScrollIndex = 0;
    private int todoScrollIndex = 0;

    private Stage primaryStage;
    private Logic logic;

    private WelcomeWindow welcomeWindow;

    // Independent Ui parts residing in this Ui container
    private static StatusBarFooter statusBarFooter;
    private TaskListPanel deadlineTaskListPanel;
    private TaskListPanel eventTaskListPanel;
    private TaskListPanel todoTaskListPanel;
    private CommandBox commandBox;

    private JFXDialog dialog;

    private Config config;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem undoMenuItem;

    @FXML
    private MenuItem redoMenuItem;

    @FXML
    private MenuItem listDoneMenuItem;

    @FXML
    private MenuItem listUndoneMenuItem;

    @FXML
    private MenuItem listAllMenuItem;

    @FXML
    private AnchorPane deadlineListPanelPlaceholder;

    @FXML
    private AnchorPane eventListPanelPlaceholder;

    @FXML
    private AnchorPane todoListPanelPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane statusbarPlaceholder;

    @FXML
    private StackPane dialogStackPane;
    private WebView browser;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());

        loadFonts(scene);
        loadStyleSheets(scene);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(ICON_IMG));
        setAccelerators();
    }

    public static StatusBarFooter getStatusBarFooter() {
        return statusBarFooter;
    }

    private void loadFonts(Scene scene) {
        Font.loadFont(MainWindow.class.getResource(FONT_AVENIR).toExternalForm(), 10);
    }

    private void loadStyleSheets(Scene scene) {
        scene.getStylesheets().add(DONE_STYLESHEET);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(exitMenuItem, KeyCombination.valueOf("Shortcut + E"));
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(undoMenuItem, KeyCombination.valueOf("Shortcut + Z"));
        setAccelerator(redoMenuItem, KeyCombination.valueOf("Shortcut + R"));
        setAccelerator(listDoneMenuItem, KeyCombination.valueOf("Shortcut + Shift + D"));
        setAccelerator(listUndoneMenuItem, KeyCombination.valueOf("Shortcut + Shift + U"));
        setAccelerator(listAllMenuItem, KeyCombination.valueOf("Shortcut + Shift + A"));
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
        deadlineTaskListPanel = new TaskListPanel(getDeadlineListPlaceholder(), getDoneTaskList(), TaskType.DEADLINE);
        eventTaskListPanel = new TaskListPanel(getEventListPlaceholder(), getDoneTaskList(), TaskType.EVENT);
        todoTaskListPanel = new TaskListPanel(getTodosListPlaceholder(), getDoneTaskList(), TaskType.TODO);

        new ResultDisplay(getResultDisplayPlaceholder());
        statusBarFooter = new StatusBarFooter(getStatusbarPlaceholder(), config.getToDoListFilePath());

        commandBox = new CommandBox(getCommandBoxPlaceholder(), logic);
        commandBox.focus();
        setScrollOnShiftUpDown();
    }

    private ObservableList<ReadOnlyTask> getDoneTaskList() {
        return logic.getFilteredTasksByDoneStatus();
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

    private AnchorPane getDeadlineListPlaceholder() {
        return deadlineListPanelPlaceholder;
    }

    private AnchorPane getEventListPlaceholder() {
        return eventListPanelPlaceholder;
    }

    private AnchorPane getTodosListPlaceholder() {
        return todoListPanelPlaceholder;
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
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

    //@@author A0141138N
    @FXML
    public void handleHelp() {
        JFXDialogLayout content = new JFXDialogLayout();
        browser = new WebView();
        URL help = MainApp.class.getResource(HELPWINDOW_URL);
        browser.getEngine().load(help.toString());
        hideScrollBar(browser);
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        content.setBody(browser);
        closeDialog();
        EventsCenter.getInstance().post(new NewResultAvailableEvent(HelpCommand.SHOWING_HELP_MESSAGE));
        dialog = new JFXDialog(dialogStackPane, content, JFXDialog.DialogTransition.CENTER, true);
        dialog.show();
        setBrowserCloseListener();
    }

    private void setBrowserCloseListener() {
        closeDialogOnNextKeyPress();
        browser.setOnKeyReleased((KeyEvent ke) -> {
            KeyCode code = ke.getCode();
            if (code == KeyCode.UP || code == KeyCode.DOWN) {
                return;
            }
            ke.consume();
            closeDialog();
            commandBox.resetKeyListener();
            commandBox.focus();
        });
    }

    private void hideScrollBar(WebView browser) {
        browser.getChildrenUnmodifiable().addListener((ListChangeListener<Node>) c -> browser
                .lookupAll(".scroll-bar")
                .forEach((node) -> node.setVisible(false)));
    }

    //@@author A0141138N
    @FXML
    public void handleHelpUG() {
        JFXDialogLayout content = new JFXDialogLayout();
        browser = new WebView();
        browser.getEngine().load(USERGUIDE_URL);
        hideScrollBar(browser);
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        content.setBody(browser);
        closeDialog();
        EventsCenter.getInstance().post(new NewResultAvailableEvent(HelpCommand.SHOWING_HELP_MESSAGE_USERGUIDE));
        dialog = new JFXDialog(dialogStackPane, content, JFXDialog.DialogTransition.CENTER, true);
        dialog.show();
        setBrowserCloseListener();
    }

    //@@author A0135739W
    public void handleTags(String tagsString) {
        JFXDialogLayout content = new JFXDialogLayout();
        Text headerText = new Text("List of All Tags:");
        Text tagsText = new Text("\n" + tagsString);
        headerText.setWrappingWidth(MIN_WIDTH);
        tagsText.setWrappingWidth(MIN_WIDTH);
        content.setHeading(headerText);
        content.setBody(tagsText);
        closeDialog();
        dialog = new JFXDialog(dialogStackPane, content, JFXDialog.DialogTransition.CENTER, true);
        dialog.show();
        closeDialogOnNextKeyPress();
    }

    //@@author
    @FXML
    public void handleUndo() {
        commandBox.handleCommands(UndoCommand.COMMAND_WORD);
    }

    @FXML
    public void handleRedo() {
        commandBox.handleCommands(RedoCommand.COMMAND_WORD);
    }

    @FXML
    public void handleListDone() {
        commandBox.handleCommands(LIST_DONE_COMMAND_INPUT);
    }

    @FXML
    public void handleListUndone() {
        commandBox.handleCommands(LIST_UNDONE_COMMAND_INPUT);
    }

    @FXML
    public void handleListAll() {
        commandBox.handleCommands(LIST_ALL_COMMAND_INPUT);
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

    // @@author A0143029M
    public TaskListPanel getDeadlineTaskListPanel() {
        return this.deadlineTaskListPanel;
    }

    public TaskListPanel getEventTaskListPanel() {
        return this.eventTaskListPanel;
    }

    public TaskListPanel getTodoTaskListPanel() {
        return this.todoTaskListPanel;
    }

    public TaskListPanel getTaskListPanel(TaskType type) {
        switch (type) {
        case DEADLINE:
            return getDeadlineTaskListPanel();
        case EVENT:
            return getEventTaskListPanel();
        case TODO:
        default:
            return getTodoTaskListPanel();
        }
    }

    public void openDialog(ReadOnlyTask task) {
        JFXDialogLayout content = new JFXDialogLayout();
        Text nameText = new Text(task.getName().fullName);
        Text descriptionText = new Text(task.getDescription().value);
        nameText.setWrappingWidth(MIN_WIDTH);
        descriptionText.setWrappingWidth(MIN_WIDTH);
        content.setHeading(nameText);
        content.setBody(descriptionText);
        closeDialog();
        dialog = new JFXDialog(dialogStackPane, content, JFXDialog.DialogTransition.CENTER, true);
        dialog.show();
        closeDialogOnNextKeyPress();
    }

    void closeDialog() {
        if (dialog == null) {
            return;
        }
        dialog.close();
        dialog = null;
        commandBox.focus();
    }

    public void showWelcomeDialog() {
        welcomeWindow = new WelcomeWindow(logic);
        JFXDialogLayout content = new JFXDialogLayout();
        Region root = welcomeWindow.todayTaskListPanel.getRoot();
        Label header = new Label();
        if (!welcomeWindow.todayTaskListPanel.isEmpty) {
            header.setText(WelcomeWindow.WELCOME);
        } else {
            header.setText(WelcomeWindow.DEFAULT);
        }
        content.setHeading(header);
        content.setBody(root);
        dialog = new JFXDialog(dialogStackPane, content, JFXDialog.DialogTransition.CENTER, true);
        dialog.show();
        closeDialogOnNextKeyPress();
    }

    private void closeDialogOnNextKeyPress() {
        commandBox.setKeyListener((KeyEvent ke) -> {
            KeyCode code = ke.getCode();
            if (code == KeyCode.UP || code == KeyCode.DOWN) {
                return;
            }
            ke.consume();
            closeDialog();
            commandBox.resetKeyListener();
            commandBox.focus();
        });
    }

    private void setScrollOnShiftUpDown() {
        commandBox.setScrollKeyListener((KeyEvent ke) -> {
            KeyCode code = ke.getCode();
            if (!(code == KeyCode.UP || code == KeyCode.DOWN)) {
                return;
            }
            if (dialog == null && ke.isShiftDown()) {
                int maxDeadlineIndex = deadlineTaskListPanel.getNumberOfItems() - 1;
                int maxEventIndex = eventTaskListPanel.getNumberOfItems() - 1;
                int maxTodoIndex = todoTaskListPanel.getNumberOfItems() - 1;
                deadlineScrollIndex = getScrollIndex(ke.getCode(), maxDeadlineIndex, deadlineScrollIndex, SCROLL_AMT);
                eventScrollIndex = getScrollIndex(ke.getCode(), maxEventIndex, eventScrollIndex, SCROLL_AMT);
                todoScrollIndex = getScrollIndex(ke.getCode(), maxTodoIndex, todoScrollIndex, SCROLL_AMT);
                deadlineTaskListPanel.viewScrollTo(deadlineScrollIndex);
                eventTaskListPanel.viewScrollTo(eventScrollIndex);
                todoTaskListPanel.viewScrollTo(todoScrollIndex);
            } else {
                scrollBrowser(ke);
            }
        });
    }

    private void scrollBrowser(KeyEvent ke) {
        if (browser == null) {
            return;
        }
        switch (ke.getCode()) {
        case UP:
            browser.fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "",
                    KeyCode.UP, true, true, true, true));
            break;
        case DOWN:
            browser.fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "",
                    KeyCode.DOWN, true, true, true, true));
            break;
        default:
            break;
        }
        browser.fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "",
                KeyCode.ESCAPE, true, true, true, true));
        browser.requestFocus();
    }

    private int getScrollIndex(KeyCode code, int maxIndex, int originalIndex, int scrollAmount) {
        int index = originalIndex;
        if (code == KeyCode.UP) {
            index -= scrollAmount;
        } else if (code == KeyCode.DOWN) {
            index += scrollAmount;
        }
        index = index < 0 ? 0 : index > maxIndex ? maxIndex : index;
        return index;
    }

}
