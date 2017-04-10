package seedu.task.ui;

import java.io.File;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import seedu.task.commons.core.Config;
import seedu.task.commons.core.GuiSettings;
import seedu.task.commons.events.storage.ExportRequestEvent;
import seedu.task.commons.events.storage.ImportRequestEvent;
import seedu.task.commons.events.storage.LoadRequestEvent;
import seedu.task.commons.events.storage.TargetFileRequestEvent;
import seedu.task.commons.events.ui.ExitAppRequestEvent;
import seedu.task.commons.util.FileUtil;
import seedu.task.logic.Logic;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends Window {

    protected static final String ICON = "/images/address_book_32.png";
    protected static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private TaskListPanel personListPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private AnchorPane personListPanelPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane statusbarPlaceholder;

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private MenuItem loadMenuItem;

    @FXML
    private MenuItem exportMenuItem;

    @FXML
    private MenuItem importMenuItem;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem themeMenuItem;

    @FXML
    private MenuItem exitMenuItem;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize(MIN_HEIGHT, MIN_WIDTH);
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        getStage().setScene(scene);
        Theme.changeTheme(getRoot(), prefs.getGuiSettings().getStyleSheet());
        setAccelerators();
    }

    private void setAccelerators() {
        setAccelerator(saveMenuItem,   KeyCombination.valueOf("ctrl+s"));
        setAccelerator(loadMenuItem,   KeyCombination.valueOf("ctrl+o"));
        setAccelerator(exportMenuItem, KeyCombination.valueOf("ctrl+e"));
        setAccelerator(importMenuItem, KeyCombination.valueOf("ctrl+i"));
        setAccelerator(helpMenuItem,   KeyCombination.valueOf("ctrl+h"));
        setAccelerator(themeMenuItem,  KeyCombination.valueOf("ctrl+t"));
        setAccelerator(exitMenuItem,  KeyCombination.valueOf("ctrl+q"));
    }

    void fillInnerParts() {
        personListPanel = new TaskListPanel(getPersonListPlaceholder(), logic.getFilteredTaskList());
        new ResultDisplay(getResultDisplayPlaceholder());
        new StatusBarFooter(getStatusbarPlaceholder(), config.getTaskManagerFilePath(), prefs);
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

    private AnchorPane getPersonListPlaceholder() {
        return personListPanelPlaceholder;
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(
                stage.getWidth(),
                stage.getHeight(),
                (int) stage.getX(),
                (int) stage.getY(),
                Paths.get(getRoot().getStylesheets().get(0)).getFileName().toString().replaceFirst("[.][^.]+$", ""),
                prefs.getGuiSettings().getLastLoadedYTomorrow());
    }

    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    //@@author A0163848R
    /**
     * Handle a JavaFX save button request.
     * Initiates a saving sequence.
     */
    @FXML
    public void handleSave() {
        File selected = FileUtil.promptSaveFileDialog("Save and Use YTomorrow File", getStage(),
                new ExtensionFilter("YTomorrow XML Files", "*.xml"));

        if (selected != null) {
            ReadOnlyTaskManager current = logic.getYTomorrow();
            raise(new ExportRequestEvent(current, selected));
            raise(new TargetFileRequestEvent(selected, prefs));
        }
    }

    /**
     * Handle a JavaFX load button request.
     * Initiates a loading sequence.
     */
    @FXML
    public void handleLoad() {
        File selected = FileUtil.promptOpenFileDialog("Load and Use YTomorrow File", getStage(),
                new ExtensionFilter("YTomorrow XML Files", "*.xml"));

        if (selected != null) {
            raise(new LoadRequestEvent(selected));
        }
    }

    /**
     * Handle a JavaFX export button request.
     * Initiates an export sequence.
     */
    @FXML
    public void handleExport() {
        File selected = FileUtil.promptSaveFileDialog("Export YTomorrow File", getStage(),
                new ExtensionFilter("YTomorrow XML Files", "*.xml"));

        if (selected != null) {
            ReadOnlyTaskManager current = logic.getYTomorrow();
            raise(new ExportRequestEvent(current, selected));
        }
    }

    /**
     * Handle a JavaFX import button request.
     * Initiates an import sequence
     */
    @FXML
    public void handleImport() {
        File selected = FileUtil.promptOpenFileDialog("Import YTomorrow File", getStage(),
                new ExtensionFilter("YTomorrow XML Files", "*.xml"));

        if (selected != null) {
            raise(new ImportRequestEvent(selected));
        }
    }

    /**
     * Handle a JavaFX theme manager button request.
     * Opens a ThemeWindow
    */
    @FXML
    public void handleTheme() {
        ThemeWindow themeWindow = new ThemeWindow(getRoot());
        themeWindow.show();
    }
    //@@author

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public TaskListPanel getTaskListPanel() {
        return this.personListPanel;
    }
}
