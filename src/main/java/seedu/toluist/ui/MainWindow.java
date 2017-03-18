package seedu.toluist.ui;

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

import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.core.GuiSettings;
import seedu.toluist.commons.events.ui.ExitAppRequestEvent;
import seedu.toluist.dispatcher.Dispatcher;
import seedu.toluist.ui.view.CommandBox;
import seedu.toluist.ui.view.ResultView;
import seedu.toluist.ui.view.TabBarView;
import seedu.toluist.ui.view.TaskListUiView;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private Stage primaryStage;
    private Dispatcher dispatcher;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private AnchorPane taskListPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane tabPanePlaceholder;

    private CommandBox commandBox;
    private TaskListUiView taskListUiView;
    private ResultView resultView;
    private TabBarView tabBarView;


    public MainWindow (Stage primaryStage, Dispatcher dispatcher) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.dispatcher = dispatcher;

        // Configure the UI
        setWindowMinSize();
        setWindowDefaultSize();
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        setAccelerators();
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

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultView contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultView.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    void hide() {
        primaryStage.hide();
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

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
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
