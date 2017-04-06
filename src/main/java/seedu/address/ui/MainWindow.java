package seedu.address.ui;

import java.io.File;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ExportRequestEvent;
import seedu.address.commons.events.ui.ImportRequestEvent;
import seedu.address.commons.events.ui.TargetFileRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.logic.Logic;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.YTomorrow;
import seedu.address.model.task.ReadOnlyPerson;

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
    private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private AnchorPane browserPlaceholder;

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

        //@@author A0163848R
        ThemeManager.changeTheme(getRoot(), prefs.getGuiSettings().getStyleSheet());
        //@@author

        setAccelerators();
    }

    private void setAccelerators() {
        setAccelerator(saveMenuItem,   KeyCombination.valueOf("ctrl+s"));
        setAccelerator(loadMenuItem,   KeyCombination.valueOf("ctrl+v"));
        setAccelerator(exportMenuItem, KeyCombination.valueOf("ctrl+e"));
        setAccelerator(importMenuItem, KeyCombination.valueOf("ctrl+i"));
        setAccelerator(helpMenuItem,   KeyCombination.valueOf("ctrl+h"));
        setAccelerator(themeMenuItem,  KeyCombination.valueOf("ctrl+c"));
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

    void fillInnerParts() {
        browserPanel = new BrowserPanel(browserPlaceholder);
        personListPanel = new PersonListPanel(getPersonListPlaceholder(), logic.getFilteredPersonList());
        new ResultDisplay(getResultDisplayPlaceholder());
        new StatusBarFooter(getStatusbarPlaceholder(), config.getAddressBookFilePath());
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
    @FXML
    public void handleSave() {
        File selected = FileUtil.promptSaveFileDialog("Save and Use YTomorrow File", getStage(),
                new ExtensionFilter("YTomorrow XML Files", "*.xml"));
        
        if (selected != null) {
            ReadOnlyAddressBook current = logic.getYTomorrow();
            raise(new ExportRequestEvent(current, selected));
            raise(new TargetFileRequestEvent(selected, prefs));
        }
    }
    
    @FXML
    public void handleLoad() {
        File selected = FileUtil.promptOpenFileDialog("Load and Use YTomorrow File", getStage(),
                new ExtensionFilter("YTomorrow XML Files", "*.xml"));
        
        if (selected != null) {
            YTomorrow readIn = new YTomorrow();
            raise(new ImportRequestEvent(readIn, selected));
            logic.importYTomorrow(readIn);
            raise(new TargetFileRequestEvent(selected, prefs));
        }
    }
    
    @FXML
    public void handleExport() {
        File selected = FileUtil.promptSaveFileDialog("Export YTomorrow File", getStage(),
                new ExtensionFilter("YTomorrow XML Files", "*.xml"));
        
        if (selected != null) {
            ReadOnlyAddressBook current = logic.getYTomorrow();
            raise(new ExportRequestEvent(current, selected));
        }
    }
    
    @FXML
    public void handleImport() {
        File selected = FileUtil.promptOpenFileDialog("Import YTomorrow File", getStage(),
                new ExtensionFilter("YTomorrow XML Files", "*.xml"));
        
        if (selected != null) {
            YTomorrow readIn = new YTomorrow();
            raise(new ImportRequestEvent(readIn, selected));
            logic.importYTomorrow(readIn);
        }
    }
    
    @FXML
    public void handleTheme() {
        ThemeWindow themeWindow = new ThemeWindow(getRoot(), prefs);
        themeWindow.fillInnerParts();
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

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    void loadPersonPage(ReadOnlyPerson person) {
        browserPanel.loadPersonPage(person);
    }

    void releaseResources() {
        browserPanel.freeResources();
    }

}
