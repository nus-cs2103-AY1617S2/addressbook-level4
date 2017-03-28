//@@author A0131125Y
package seedu.toluist.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.core.GuiSettings;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.events.ui.ExitAppRequestEvent;
import seedu.toluist.commons.util.FxViewUtil;
import seedu.toluist.dispatcher.Dispatcher;
import seedu.toluist.ui.view.CommandAutoCompleteView;
import seedu.toluist.ui.view.CommandBox;
import seedu.toluist.ui.view.ResultView;
import seedu.toluist.ui.view.TabBarView;
import seedu.toluist.ui.view.TaskListUiView;

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
    private AnchorPane commandAutoCompletePlaceholder;

    private CommandBox commandBox;
    private TaskListUiView taskListUiView;
    private ResultView resultView;
    private TabBarView tabBarView;
    private CommandAutoCompleteView commandAutoCompleteView;


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
        configureKeyCombinations();
        configureChildrenViews();
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
    }

    private void configureKeyCombinations() {
        configureSwitchTabKeyCombinations();
    }

    void hide() {
        primaryStage.hide();
    }

    private void configureSwitchTabKeyCombinations() {
        String[] tabNames = new String[] { "i", "t", "n", "c", "a" };
        Arrays.stream(tabNames).forEach(tabName -> {
            KeyCombination keyCombination = new KeyCodeCombination(getKeyCode(tabName),
                    KeyCombination.CONTROL_DOWN);
            String switchCommand = "switch " + tabName;
            EventHandler<ActionEvent> handler = event -> dispatcher.dispatch(switchCommand);
            FxViewUtil.setKeyCombination(getRoot(), keyCombination, handler);
        });
    }

    /**
     * Get matching key code for a string
     * @param s string
     * @returna key code
     */
    private KeyCode getKeyCode(String s) {
        switch (s) {
        case "i": return KeyCode.I;
        case "t": return KeyCode.T;
        case "n": return KeyCode.N;
        case "c": return KeyCode.C;
        case "a": return KeyCode.A;
        default: return KeyCode.ESCAPE;
        }
    }

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

    private AnchorPane getCommandAutoCompletePlaceholder() {
        return commandAutoCompletePlaceholder;
    }

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
            Class applicationClass = Class.forName("com.apple.eawt.Application");
            Method getApplication = applicationClass.getMethod("getApplication");
            Object application = getApplication.invoke(applicationClass);
            Method setDockIconImage = applicationClass.getMethod("setDockIconImage", java.awt.Image.class);
            setDockIconImage.invoke(application,
                    new ImageIcon(MainWindow.class.getResource(IMAGE_PATH_LOGO)).getImage());
        } catch (NoSuchMethodException | IllegalAccessException
                | InvocationTargetException | ClassNotFoundException e) {
            logger.info("Not on macOS");
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
        taskListUiView = new TaskListUiView();
        taskListUiView.setParent(getTaskListPlaceholder());

        commandBox = new CommandBox(dispatcher);
        commandBox.setParent(getCommandBoxPlaceholder());

        resultView = new ResultView();
        resultView.setParent(getResultDisplayPlaceholder());

        tabBarView = new TabBarView();
        tabBarView.setParent(getTabPanePlaceholder());

        commandAutoCompleteView = new CommandAutoCompleteView();
        commandAutoCompleteView.setParent(getCommandAutoCompletePlaceholder());
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

    /** ================ ACTION HANDLERS ================== **/

    @FXML
    public void handleHelp() {
    }

    @FXML
    public void handleMenu() {
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }
}
