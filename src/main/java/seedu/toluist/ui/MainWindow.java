//@@author A0131125Y
package seedu.toluist.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.core.GuiSettings;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.dispatcher.Dispatcher;
import seedu.toluist.ui.view.CommandAutoCompleteView;
import seedu.toluist.ui.view.CommandBox;
import seedu.toluist.ui.view.HelpListView;
import seedu.toluist.ui.view.ResultView;
import seedu.toluist.ui.view.StatusBar;
import seedu.toluist.ui.view.TabBarView;
import seedu.toluist.ui.view.TaskListView;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(MainWindow.class);
    private static final String IMAGE_PATH_LOGO = "/images/logo.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 800;

    private static final String COMMAND_UNDO = "undo";
    private static final String COMMAND_REDO = "redo";
    private static final String COMMAND_SWITCH = "switch ";
    private static final String COMMAND_NAVIGATEHISTORY = "navigatehistory ";
    private static final String[] KEYCODES_NAVIGATEHISTORY = new String[] { "up", "down" };
    private static final String[] KEYCODES_SWITCH = new String[] { "1", "2", "3", "4", "5" };

    private static final String APPLICATION_CLASS = "com.apple.eawt.Application";
    private static final String METHOD_GET_APPLICATION = "getApplication";
    private static final String METHOD_SET_DOCK_ICON = "setDockIconImage";

    private Stage primaryStage;
    private Dispatcher dispatcher;

    @FXML
    private AnchorPane commandBoxPlaceholder;
    @FXML
    private AnchorPane taskListPlaceholder;
    @FXML
    private AnchorPane resultDisplayPlaceholder;
    @FXML
    private AnchorPane tabPanePlaceholder;
    @FXML
    private HBox commandAutoCompletePlaceholder;
    @FXML
    private BorderPane commandAutoCompleteContainer;
    @FXML
    private AnchorPane helpPlaceholder;
    @FXML
    private AnchorPane statusBarPlaceholder;

    private CommandBox commandBox;
    private TaskListView taskListUiView;
    private ResultView resultView;
    private TabBarView tabBarView;
    private CommandAutoCompleteView commandAutoCompleteView;
    private HelpListView helpListView;
    private StatusBar statusBar;


    public MainWindow (Stage primaryStage, Dispatcher dispatcher) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.dispatcher = dispatcher;

        // Configure the UI
        setLogo();
        setWindowMinSize();
        setWindowDefaultSize();
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        configureChildrenViews();
        configureKeyCombinations();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void render() {
        taskListUiView.render();
        commandBox.render();
        resultView.render();
        tabBarView.render();
        commandAutoCompleteView.render();
        helpListView.render();
        statusBar.render();
    }

    private void configureKeyCombinations() {
        configureSwitchTabKeyCombinations();
        configureUndoKeyCombination();
        configureRedoKeyCombination();
        configureHistoryNavigationKeyPresses();
        configureExitHelpKeyPress();
    }

    void hide() {
        primaryStage.hide();
    }

    /**
     * Configure switch tab hotkey, using underlined letters
     */
    private void configureSwitchTabKeyCombinations() {
        Arrays.stream(KEYCODES_SWITCH).forEach(tabName -> {
            KeyCombination keyCombination = new KeyCodeCombination(getKeyCode(tabName),
                    KeyCombination.CONTROL_DOWN);
            String switchCommand = COMMAND_SWITCH + tabName;
            EventHandler<ActionEvent> handler = event -> dispatcher.dispatch(switchCommand);
            FxViewUtil.setKeyCombination(getRoot(), keyCombination, handler);
        });
    }

    /**
     * Configure CTRL+Z for quick undo
     */
    private void configureUndoKeyCombination() {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        String undoCommand = COMMAND_UNDO;
        EventHandler<ActionEvent> handler = event -> dispatcher.dispatch(undoCommand);
        FxViewUtil.setKeyCombination(getRoot(), keyCombination, handler);
    }

    /**
     * Configure CTRL+Y for quick redo
     */
    private void configureRedoKeyCombination() {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
        String redoCommand = COMMAND_REDO;
        EventHandler<ActionEvent> handler = event -> dispatcher.dispatch(redoCommand);
        FxViewUtil.setKeyCombination(getRoot(), keyCombination, handler);
    }

    //@@author A0162011A
    /**
     * Configure Up/Down for history navigation
     */
    private void configureHistoryNavigationKeyPresses() {
        Arrays.stream(KEYCODES_NAVIGATEHISTORY).forEach(keyName -> {
            KeyCode keycode = getKeyCode(keyName);
            String navigateCommand = COMMAND_NAVIGATEHISTORY + keyName;
            EventHandler<ActionEvent> handler = event -> dispatcher.dispatch(navigateCommand);
            FxViewUtil.setKeyCode(commandBox.getRoot(), keycode, handler);
        });
    }

    //@@author A0131125Y
    /**
     * Configure keys for exitting help
     */
    private void configureExitHelpKeyPress() {
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            UiStore store = UiStore.getInstance();
            store.setHelp(null, new ArrayList<>());
        });
    }

    //@@author
    /**
     * Get matching key code for a string
     * @param s string
     * @return a key code
     */
    private KeyCode getKeyCode(String s) {
        switch (s) {
        case "1": return KeyCode.DIGIT1;
        case "2": return KeyCode.DIGIT2;
        case "3": return KeyCode.DIGIT3;
        case "4": return KeyCode.DIGIT4;
        case "5": return KeyCode.DIGIT5;
        case "up": return KeyCode.UP;
        case "down": return KeyCode.DOWN;
        default: return KeyCode.ESCAPE;
        }
    }

    //@@author A0131125Y
    private AnchorPane getTaskListPlaceholder() {
        return taskListPlaceholder;
    }

    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    private AnchorPane getTabPanePlaceholder() {
        return tabPanePlaceholder;
    }

    private HBox getCommandAutoCompletePlaceholder() {
        return commandAutoCompletePlaceholder;
    }

    private AnchorPane getStatusBarPlaceholder() {
        return statusBarPlaceholder;
    }

    /**
     * Set min size for window
     */
    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Sets the logo for the app
     */
    private void setLogo() {
        FxViewUtil.setStageIcon(primaryStage, IMAGE_PATH_LOGO);
        // Only in macOS, you can try to use reflection to access this library
        // and use it to set a custom app icon
        try {
            Class applicationClass = Class.forName(APPLICATION_CLASS);
            Method getApplication = applicationClass.getMethod(METHOD_GET_APPLICATION);
            Object application = getApplication.invoke(applicationClass);
            Method setDockIconImage = applicationClass.getMethod(METHOD_SET_DOCK_ICON, java.awt.Image.class);
            setDockIconImage.invoke(application,
                    new ImageIcon(MainWindow.class.getResource(IMAGE_PATH_LOGO)).getImage());
        } catch (NoSuchMethodException | IllegalAccessException
                | InvocationTargetException | ClassNotFoundException e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * Sets the default size based on user config.
     */
    private void setWindowDefaultSize() {
        GuiSettings guiSettings = Config.getInstance().getGuiSettings();
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    private void configureChildrenViews() {
        taskListUiView = new TaskListView();
        taskListUiView.setParent(getTaskListPlaceholder());

        commandBox = new CommandBox(dispatcher);
        commandBox.setParent(getCommandBoxPlaceholder());

        resultView = new ResultView();
        resultView.setParent(getResultDisplayPlaceholder());

        tabBarView = new TabBarView();
        tabBarView.setParent(getTabPanePlaceholder());

        commandAutoCompleteContainer.setPickOnBounds(false);
        commandAutoCompleteView = new CommandAutoCompleteView();
        commandAutoCompleteView.setParent(getCommandAutoCompletePlaceholder());

        FxViewUtil.makeFullWidth(helpPlaceholder);
        helpListView = new HelpListView();
        helpListView.setParent(helpPlaceholder);

        statusBar = new StatusBar();
        statusBar.setParent(getStatusBarPlaceholder());
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }
}
