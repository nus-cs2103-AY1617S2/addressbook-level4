package t09b1.today.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;

import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import t09b1.today.commons.core.Config;
import t09b1.today.commons.core.GuiSettings;
import t09b1.today.commons.core.LogsCenter;
import t09b1.today.commons.events.ui.ExitAppRequestEvent;
import t09b1.today.commons.events.ui.NewResultAvailableEvent;
import t09b1.today.commons.events.ui.UpdateStatusBarEvent;
import t09b1.today.commons.util.FxViewUtil;
import t09b1.today.logic.Logic;
import t09b1.today.logic.commands.CommandResult;
import t09b1.today.logic.commands.NotTodayCommand;
import t09b1.today.logic.commands.UseThisCommand;
import t09b1.today.logic.commands.exceptions.CommandException;
import t09b1.today.model.UserPrefs;
import t09b1.today.model.task.ReadOnlyTask;
import t09b1.today.ui.util.CommandTextFieldValidator;

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
    private HBox windowTop;

    @FXML
    private AnchorPane windowCentre;

    CommandTextFieldValidator validator;

    /** Amount that is scrolled using the down and up arrow keys */
    private static final double SCROLL_AMOUNT = 0.05;

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
                "find", "help", "list", "listcompleted", "notdone", "redo", "renametag", "undo", "saveto", "today",
                NotTodayCommand.COMMAND_WORD, UseThisCommand.COMMAND_WORD);

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        // prevent initialising style when stage is set to visible
        if (primaryStage.getStyle() != StageStyle.UNDECORATED) {
            primaryStage.initStyle(StageStyle.UNDECORATED);
        }
        primaryStage.setResizable(false);
        registerAsAnEventHandler(this);

        // Allows dragging the undecorated window around
        final Pair coordinate = new Pair();
        windowTop.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                coordinate.x = primaryStage.getX() - mouseEvent.getScreenX();
                coordinate.y = primaryStage.getY() - mouseEvent.getScreenY();
            }
        });

        windowTop.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                primaryStage.setX(mouseEvent.getScreenX() + coordinate.x);
                primaryStage.setY(mouseEvent.getScreenY() + coordinate.y);
            }
        });

        // set validator for commandTextField(to display error message under the
        // command box)
        validator = new CommandTextFieldValidator();
        validator.setMessage("");
        validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.WARNING).size("1em")
                .styleClass("error").build());
        commandTextField.getValidators().add(validator);
    }

    // @@author A0139388M
    /** Bind key combinations to scene */
    private void bindKeysToScene() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {
                setHotKeys(keyEvent);
                setScrollingKeys(keyEvent);
            }
        });
    }

    /** Set hotkeys for today and future tasklists */
    private void setHotKeys(KeyEvent keyEvent) {
        KeyCodeCombination todayKey = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_ANY);
        KeyCodeCombination futureKey = new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_ANY);
        TitledPane todayPanel = taskListPanel.getTodayTaskListPanel();
        TitledPane futurePanel = taskListPanel.getFutureTaskListPanel();

        if (todayKey.match(keyEvent)) {
            todayPanel.setExpanded(!(todayPanel.isExpanded()));
        } else if (futureKey.match(keyEvent)) {
            futurePanel.setExpanded(!(futurePanel.isExpanded()));
        }
    }

    /** Set scrolling using up and down arrow keys */
    private void setScrollingKeys(KeyEvent keyEvent) {
        ScrollPane scrollPane = taskListPanel.getScrollPane();
        if (keyEvent.getCode() == KeyCode.DOWN) {
            scrollPane.setVvalue(scrollPane.getVvalue() + SCROLL_AMOUNT);
        } else if (keyEvent.getCode() == KeyCode.UP) {
            scrollPane.setVvalue(scrollPane.getVvalue() - SCROLL_AMOUNT);
        }
    }
    // @@author

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    // @@author A0144315N
    /**
     * Initialises observable lists for ListView and loads other UI components
     */
    void fillInnerParts() {
        // Initialises observable lists for ListViews
        taskListToday = FXCollections.observableArrayList();
        taskListFuture = FXCollections.observableArrayList();
        taskListCompleted = FXCollections.observableArrayList();

        // Split and sort task lists (see Developer Guide "Implementation"
        // section for detail)
        prepareTaskList();

        // Load UI components
        taskListPanel = new TaskListPanel(getTaskListPlaceholder(), taskListToday, taskListFuture);
        new StatusBarFooter(getStatusbarPlaceholder(), config.getTaskManagerFilePath());
        new ResultDisplay(getResultDisplayPlaceholder());
        completedTaskListPanel = new CompletedTaskListPanel(getCompletedTaskListPlaceholder(), taskListCompleted);

        bindKeysToScene();
    }

    /**
     * Prepares categorised task list for today/future/completed ListView
     */
    public void prepareTaskList() {
        // Assume the three lists will always be initialised by fillInnerParts()
        assert taskListToday != null;
        assert taskListFuture != null;
        assert taskListCompleted != null;

        logic.prepareTaskList(taskListToday, taskListFuture, taskListCompleted);
    }

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

    // @@author A0144315N
    /**
     * Handles command execution for commandTextField input
     */
    @FXML
    private void handleCommandInputChanged() {
        // Do nothing on empty command box
        if (commandTextField.getText().equals("")) {
            return;
        }

        validator.hideErrorMessage();
        commandTextField.validate();

        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            // process result of the command
            commandTextField.setText("");
            raise(new NewResultAvailableEvent(commandResult.getFeedbackToUser()));
            raise(new UpdateStatusBarEvent(commandResult.getStatusBarMessage()));
        } catch (CommandException e) {
            // handle command failure
            // display error message under command box
            validator.setMessage(e.getMessage());
            validator.showErrorMessage();
            commandTextField.validate();
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * Displays a snack bar message at the bottom of the main window
     */
    @Subscribe
    public void handleUpdateStatusBarEvent(UpdateStatusBarEvent event) {
        JFXSnackbar toast = new JFXSnackbar(windowCentre);
        toast.show(event.getMessage(), 4000);
    }
}

// A coordinate pair class used to store mouse position
class Pair {
    public double x, y;
}
