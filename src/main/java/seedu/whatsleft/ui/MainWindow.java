package seedu.whatsleft.ui;

import java.time.LocalDateTime;
import java.util.List;

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
import seedu.whatsleft.commons.core.Config;
import seedu.whatsleft.commons.core.GuiSettings;
import seedu.whatsleft.commons.events.ui.ExitAppRequestEvent;
import seedu.whatsleft.commons.util.FxViewUtil;
import seedu.whatsleft.logic.Logic;
import seedu.whatsleft.model.UserPrefs;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;


//@@author A0148038A
//@@author A0124377A
/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/WhatsLeft.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 500;
    private static final int MIN_WIDTH = 450;

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private EventListPanel eventListPanel;
    private TaskListPanel taskListPanel;
    private Config config;
    private CalendarPanel calendarPanel;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private AnchorPane eventListPanelPlaceholder;

    @FXML
    private AnchorPane taskListPanelPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane statusbarPlaceholder;

    @FXML
    private AnchorPane calendarPlaceholder;

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
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    void fillInnerParts() {
        eventListPanel = new EventListPanel(getEventListPlaceholder(), logic.getFilteredEventList());
        taskListPanel = new TaskListPanel(getTaskListPlaceholder(), logic.getFilteredTaskList());
        calendarPanel = new CalendarPanel(getCalendarPlaceholder(),
                logic.getFilteredEventList(), logic.getFilteredTaskList());
        new ResultDisplay(getResultDisplayPlaceholder());
        new StatusBarFooter(getStatusbarPlaceholder(), config.getWhatsLeftFilePath());
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

    private AnchorPane getEventListPlaceholder() {
        return eventListPanelPlaceholder;
    }

    private AnchorPane getTaskListPlaceholder() {
        return taskListPanelPlaceholder;
    }

    private AnchorPane getCalendarPlaceholder() {
        return calendarPlaceholder;
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    /**
     * Sets the default size based on user preferences. Maximised on default
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setMaximized(true);
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
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
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

    public EventListPanel getEventListPanel() {
        return this.eventListPanel;
    }

    public TaskListPanel getTaskListPanel() {
        return this.taskListPanel;
    }

    public CalendarPanel getCalendarPanel() {
        return this.calendarPanel;
    }

    /**
     * Allows the calendar to be updated to the latest list after changes
     * have been made
     * @param eventlist, tasklist
     */
    public void updateCalendar(List<ReadOnlyEvent> eventList, List<ReadOnlyTask> taskList) {
        this.calendarPanel.refresh(eventList, taskList);
    }

    /**
     * Allows the calendar view to be changed accordingly
     * @param selectedDateTime
     */
    public void changeCalendarView(LocalDateTime nextDateTime) {
        this.calendarPanel.updateCalendarShownPeriod(nextDateTime);
    }

}
