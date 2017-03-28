//@@author A0131125Y
package seedu.toluist;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.core.EventsCenter;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.core.Version;
import seedu.toluist.commons.events.ui.ExitAppRequestEvent;
import seedu.toluist.commons.exceptions.DataStorageException;
import seedu.toluist.dispatcher.CommandDispatcher;
import seedu.toluist.dispatcher.Dispatcher;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);

    protected Ui ui;
    protected Dispatcher dispatcher;
    protected Config config = Config.getInstance();


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ToLuist ]===========================");
        super.init();

        initLogging(config);
        initDispatcher();
        initDispatcher();
        initUi();
        initModel();
        initEventsCenter();
    }

    private void initDispatcher() {
        dispatcher = new CommandDispatcher();
    }

    private void initUi() {
        ui = UiManager.getInstance();
        ui.init(dispatcher);
    }

    private void initModel() {
        try {
            TodoList.getInstance().load();
        } catch (DataStorageException e) {
            logger.info("No existing data file found");
        }
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting ToLuist " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping ToLuist ] =============================");
        ui.stop();
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
