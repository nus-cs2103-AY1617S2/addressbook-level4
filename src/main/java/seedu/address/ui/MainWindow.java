package seedu.address.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;

import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.UpdateStatusBarEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;

/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(MainWindow.class);

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private Stage primaryStage;
    private Logic logic;
    private Scene scene;

    // Independent Ui parts residing in this Ui container
    private TaskListPanel taskListPanel;
    private CompletedTaskListPanel completedTaskListPanel;
    private Config config;

    // Categorised Task List
    public ObservableList<ReadOnlyTask> taskListToday;
    private ObservableList<ReadOnlyTask> taskListFuture;
    private ObservableList<ReadOnlyTask> taskListCompleted;

    @FXML
    private Label titleDate;

    @FXML
    private Label titleDay;

    // @FXML
    // private AnchorPane commandBoxPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane taskListPanelPlaceholder;

    @FXML
    private AnchorPane statusbarPlaceholder;

    @FXML
    private AnchorPane completedTaskListPlaceholder;

    @FXML
    private JFXTextField commandTextField;

    @FXML
    private Label commandResult;

    // @@author A0144315N
    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;

        // Set date
        Calendar cal = Calendar.getInstance();
        this.titleDate.setText(new SimpleDateFormat("dd MMM").format(cal.getTime()));
        this.titleDay.setText(new SimpleDateFormat("EE").format(cal.getTime()));
        this.commandTextField.setLabelFloat(true);
        TextFields.bindAutoCompletion(commandTextField, "add", "clear", "delete", "deletetag", "done", "edit", "exit",
                "find", "help", "list", "listcompleted", "notdone", "redo", "renametag", "undo", "saveto", "today");
        this.commandResult.setText("");

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        registerAsAnEventHandler(this);
    }

    // @@author
    /** Set hotkeys for today and future tasklists */
    private void setHotKeys() {
        KeyCodeCombination todayKey = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_ANY);
        KeyCodeCombination futureKey = new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_ANY);
        TitledPane todayPanel = taskListPanel.getTodayTaskListPanel();
        TitledPane futurePanel = taskListPanel.getFutureTaskListPanel();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {
                if (todayKey.match(keyEvent)) {
                    todayPanel.setExpanded(!(todayPanel.isExpanded()));
                } else if (futureKey.match(keyEvent)) {
                    futurePanel.setExpanded(!(futurePanel.isExpanded()));
                }
            }
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets hotkeys for tasklists to expand and minimize.
     */
    private void setHotKeyForTaskLists(TitledPane panel, KeyCombination k) {
    }

    // @@author A0144315N
    void fillInnerParts() {
        taskListToday = FXCollections.observableArrayList();
        taskListFuture = FXCollections.observableArrayList();
        taskListCompleted = FXCollections.observableArrayList();
        prepareTaskList();
        taskListPanel = new TaskListPanel(getTaskListPlaceholder(), taskListToday, taskListFuture);
        new StatusBarFooter(getStatusbarPlaceholder(), config.getTaskManagerFilePath());
        // new CommandBox(getCommandBoxPlaceholder(), logic);
        // TODO: show completedTaskPanel when show completed command is
        // implemented
        new ResultDisplay(getResultDisplayPlaceholder());
        completedTaskListPanel = new CompletedTaskListPanel(getCompletedTaskListPlaceholder(), taskListCompleted);

        setHotKeys();
    }

    /*
     * Prepares categorised task list for today/future/completed ListView
     *
     */
    public void prepareTaskList() {
        // Assume the three lists will always be initialised by fillInnerParts()
        assert taskListToday != null;
        assert taskListFuture != null;
        assert taskListCompleted != null;

        logic.prepareTaskList(taskListToday, taskListFuture, taskListCompleted);
    }

    /*
     * private AnchorPane getCommandBoxPlaceholder() { return
     * commandBoxPlaceholder; }
     */

    // @@author
    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }

    private AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
    }

    private AnchorPane getCompletedTaskListPlaceholder() {
        return completedTaskListPlaceholder;
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

    @FXML
    private void handleCommandInputChanged() {
        // Do nothing on empty command box
        if (commandTextField.getText().equals("")) {
            return;
        }

        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            // process result of the command
            commandTextField.setText("");
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            raise(new UpdateStatusBarEvent(commandResult.statusBarMessage));

        } catch (CommandException e) {
            // handle command failure
            raise(new NewResultAvailableEvent(e.getMessage()));
            raise(new UpdateStatusBarEvent("Invalid command. Type \"Help\" to see format."));
        }
    }

    // @@author A0144315N
    @Subscribe
    public void handleUpdateStatusBarEvent(UpdateStatusBarEvent event) {
        this.commandResult.setText(event.getMessage());
        JFXSnackbar toast = new JFXSnackbar(taskListPanelPlaceholder);
        // EventHandler handler = new EventHandler() {
        // @Override
        // public void handle(Event event) {
        // toast.close();
        // }
        // };
        // toast.show(event.getMessage(), "OK", 4000, handler);
        toast.show(event.getMessage(), 4000);
    }
}
