package seedu.doit.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.doit.commons.core.Config;
import seedu.doit.commons.core.GuiSettings;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.events.ui.ExitAppRequestEvent;
import seedu.doit.commons.events.ui.NewResultAvailableEvent;
import seedu.doit.commons.util.FxViewUtil;
import seedu.doit.logic.Logic;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.UserPrefs;
/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String ICON = "/images/task_manager.png";
    private static final String FXML = "MainWindow.fxml";
    private static final String UNDO_COMMAND = "undo";
    private static final int MIN_HEIGHT = 650;
    private static final int MIN_WIDTH = 1100;

    private Stage primaryStage;
    private Logic logic;


    // Independent Ui parts residing in this Ui container

    private TaskListPanel taskListPanel;
    private EventListPanel eventListPanel;
    private FloatingTaskListPanel fListPanel;
    private Config config;
    private Scene scene;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private AnchorPane taskListPanelPlaceholder;

    @FXML
    private AnchorPane eventListPanelPlaceholder;

    @FXML
    private AnchorPane floatingListPanelPlaceholder;

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
        this.scene = new Scene(getRoot());
        primaryStage.setScene(this.scene);
        setAccelerators();
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(this.helpMenuItem, KeyCombination.valueOf("F1"));
        this.scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            KeyCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
            @Override
            public void handle(KeyEvent evt) {
                if (this.undo.match(evt)) {
                 // handle command failure
                        try {
                            MainWindow.this.logic.execute(UNDO_COMMAND);
                    } catch (CommandException e) {
                        MainWindow.this.logger.info("Invalid command: " + UNDO_COMMAND);
                        raise(new NewResultAvailableEvent(e.getMessage()));
                    }
                }
            }
        });
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
  //@@author A0160076L
    protected void fillInnerParts() {
        this.taskListPanel = new TaskListPanel(getTaskListPlaceholder(), this.logic.getFilteredTaskList());
        this.eventListPanel = new EventListPanel(getEventListPlaceholder(), this.logic.getFilteredTaskList());
        this.fListPanel = new FloatingTaskListPanel(getFListPlaceholder(), this.logic.getFilteredTaskList());
        new ResultDisplay(getResultDisplayPlaceholder());
        new StatusBarFooter(getStatusbarPlaceholder(), this.config.getTaskManagerFilePath());
        new CommandBox(getCommandBoxPlaceholder(), this.logic);
    }
    //@@author

    private AnchorPane getCommandBoxPlaceholder() {
        return this.commandBoxPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return this.statusbarPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return this.resultDisplayPlaceholder;
    }

    private AnchorPane getTaskListPlaceholder() {
        return this.taskListPanelPlaceholder;
    }
    //@@author: A0160076L
    private AnchorPane getEventListPlaceholder() {
        return this.eventListPanelPlaceholder;
    }

    private AnchorPane getFListPlaceholder() {

        return this.floatingListPanelPlaceholder;
    }
    //@@author
    protected void hide() {
        this.primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        this.primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     *
     * @param iconSource
     *            e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(this.primaryStage, iconSource);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        this.primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        this.primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            this.primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            this.primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        this.primaryStage.setMinHeight(MIN_HEIGHT);
        this.primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    protected GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(this.primaryStage.getWidth(), this.primaryStage.getHeight(),
                (int) this.primaryStage.getX(), (int) this.primaryStage.getY());
    }

    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    void show() {
        this.primaryStage.show();
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
    //@@author A0160076L
    public EventListPanel getEventListPanel() {
        return this.eventListPanel;
    }
    public FloatingTaskListPanel getFloatingListPanel() {
        return this.fListPanel;
    }

    /**
     *
     * Handle scrollTo in different lists
     */
    public void scrollTo(int index) {
        if (index < this.logic.getFilteredTaskList().filtered(task -> !task.hasStartTime()
                && task.hasEndTime() /*&& !task.getIsDone()*/).size()) {
            this.taskListPanel.scrollTo(index);
            this.eventListPanel.clearSelection();
            this.fListPanel.clearSelection();
        } else if (index < this.logic.getFilteredTaskList().filtered(task -> !task.hasStartTime()
                && task.hasEndTime()).size() + this.logic.getFilteredTaskList().filtered(task -> task.hasStartTime()
                && task.hasEndTime() /*&& !task.getIsDone()*/).size()) {
            this.eventListPanel.scrollTo(index - this.logic.getFilteredTaskList().filtered(task -> !task.hasStartTime()
                    && task.hasEndTime() /*&& !task.getIsDone()*/).size());
            this.taskListPanel.clearSelection();
            this.fListPanel.clearSelection();
        } else {
            this.fListPanel.scrollTo(index - this.logic.getFilteredTaskList().filtered(task -> !task.hasStartTime()
                    && task.hasEndTime()).size() - this.logic.getFilteredTaskList().filtered(task -> task.hasStartTime()
                            && task.hasEndTime() /*&& !task.getIsDone()*/).size());
            this.eventListPanel.clearSelection();
            this.taskListPanel.clearSelection();
        }
    }
    //@@author
}
