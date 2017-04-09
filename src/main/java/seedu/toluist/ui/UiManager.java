//@@author A0131125Y
package seedu.toluist.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import seedu.toluist.commons.core.ComponentManager;
import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.dispatcher.Dispatcher;

/**
 * The manager of the UI component. Singleton
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String BOOTSTRAP_COMMAND = "list";
    private static final String STYLESHEET_PATH = "stylesheet/DefaultTheme.css";
    private static UiManager instance;

    private MainWindow mainWindow;
    private Dispatcher dispatcher;

    public static UiManager getInstance() {
        if (instance == null) {
            instance = new UiManager();
        }
        return instance;
    }

    private UiManager() {
        super();
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        Config config = Config.getInstance();
        primaryStage.setTitle(config.getAppTitle());

        try {
            mainWindow = new MainWindow(primaryStage, dispatcher);
            mainWindow.render();
            mainWindow.show();
            // Initial dispatch call
            dispatcher.dispatch(BOOTSTRAP_COMMAND);
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        // Save the last used gui settings
        Config.getInstance().setGuiSettings(mainWindow.getCurrentGuiSetting());
        Config.getInstance().save();
        mainWindow.hide();
    }

    public void init(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add(STYLESHEET_PATH);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + StringUtil.SINGLE_SPACE + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }
}
