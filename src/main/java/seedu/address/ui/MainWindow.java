package seedu.address.ui;

import javafx.collections.ObservableList;
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

import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.model.task.Task;
import seedu.address.ui.view.CommandBox;
import seedu.address.ui.view.ResultDisplay;
import seedu.address.ui.view.TaskListUiComponent;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainView.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private Stage primaryStage;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private AnchorPane taskListPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    public MainWindow (Stage primaryStage) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;

        // Configure the UI
        setWindowMinSize();
//        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        setAccelerators();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void render(UiStore store) {
        final ObservableList<Task> tasks = store.getUiTasks();
        new TaskListUiComponent(getTaskListPlaceholder(), tasks).render();
        new CommandBox(getCommandBoxPlaceholder()).render();
        new ResultDisplay(getResultDisplayPlaceholder()).render();
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
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
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

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

//    @FXML
//    public void handleHelp() {
//        HelpWindow helpWindow = new HelpWindow();
//        helpWindow.show();
//    }

    void show() {
        primaryStage.show();
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
