package seedu.jobs.ui;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.jobs.MainApp;
import seedu.jobs.commons.core.ComponentManager;
import seedu.jobs.commons.core.Config;
import seedu.jobs.commons.core.LogsCenter;
import seedu.jobs.commons.events.storage.ConfigChangeSavePathEvent;
import seedu.jobs.commons.events.storage.DataSavingExceptionEvent;
import seedu.jobs.commons.events.storage.DeleteCredentialEvent;
import seedu.jobs.commons.events.storage.LoginInfoChangeEvent;
import seedu.jobs.commons.events.storage.SaveLoginInfoEvent;
import seedu.jobs.commons.events.ui.BrowserDisplayEvent;
import seedu.jobs.commons.events.ui.JumpToListRequestEvent;
import seedu.jobs.commons.events.ui.ShowHelpRequestEvent;
import seedu.jobs.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.jobs.commons.util.ConfigUtil;
import seedu.jobs.commons.util.StringUtil;
import seedu.jobs.logic.Logic;
import seedu.jobs.model.LoginInfo;
import seedu.jobs.model.UserPrefs;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";
    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private LoginInfo loginInfo;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs, LoginInfo loginInfo) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
        this.loginInfo = loginInfo;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic, loginInfo);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
        mainWindow.releaseResources();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code ===============================================================

    @Subscribe
    public void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    public void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }

    @Subscribe
    public void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().scrollTo(event.targetIndex);
    }


    @Subscribe
    public void handlePersonPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

  //@@author A0130979U
    @Subscribe
    public void handleConfigChangedSavePathEvent(ConfigChangeSavePathEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        config.setTaskBookFilePath(event.getPath());
        ConfigUtil.saveConfig(config, config.DEFAULT_CONFIG_FILE);
    }

    @Subscribe
    public void handleBrowserDisplayEvent(BrowserDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.activateBrowser();
    }

    @Subscribe
    public void handleLoginInfoChangeEvent(LoginInfoChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        indicateDeleteCredentialEvent();
        setLoginInfo(event);
        indicateSaveLoginInfoEvent();
    }

    private void indicateDeleteCredentialEvent() {
        raise(new DeleteCredentialEvent(loginInfo.getCredentialFilePath()));
    }

    private void setLoginInfo(LoginInfoChangeEvent event) {
        this.loginInfo.setEmail(event.getEmail());
        this.loginInfo.setPassword(event.getPassword());
        this.loginInfo.setCredentialFilePath(event.getCredentialFilePath());
    }

    private void indicateSaveLoginInfoEvent() {
        raise(new SaveLoginInfoEvent());
    }

  //@@author
}
