package seedu.doit;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.doit.commons.core.Config;
import seedu.doit.commons.core.EventsCenter;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.core.Version;
import seedu.doit.commons.events.ui.ExitAppRequestEvent;
import seedu.doit.commons.exceptions.DataConversionException;
import seedu.doit.commons.util.ConfigUtil;
import seedu.doit.commons.util.StringUtil;
import seedu.doit.logic.Logic;
import seedu.doit.logic.LogicManager;
import seedu.doit.model.EventManager;
import seedu.doit.model.FloatingTaskManager;
import seedu.doit.model.Model;
import seedu.doit.model.ModelManager;
import seedu.doit.model.ReadOnlyEventManager;
import seedu.doit.model.ReadOnlyFloatingTaskManager;
import seedu.doit.model.ReadOnlyTaskManager;
import seedu.doit.model.TaskManager;
import seedu.doit.model.UserPrefs;
import seedu.doit.model.util.SampleDataUtil;
import seedu.doit.storage.Storage;
import seedu.doit.storage.StorageManager;
import seedu.doit.ui.Ui;
import seedu.doit.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    public static final Version VERSION = new Version(1, 0, 0, true);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing TaskManager ]===========================");
        super.init();

        this.config = initConfig(getApplicationParameter("config"));
        this.storage = new StorageManager(this.config);

        this.userPrefs = initPrefs(this.config);

        initLogging(this.config);

        this.model = initModelManager(this.storage, this.userPrefs);

        this.logic = new LogicManager(this.model, this.storage);

        this.ui = new UiManager(this.logic, this.config, this.userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyTaskManager> taskManagereOptional;
        ReadOnlyTaskManager initialTaskManagerData;
        ReadOnlyFloatingTaskManager initialFloatingTaskManagerData;
        ReadOnlyEventManager initialEventManagerData;
        try {
            taskManagereOptional = storage.readTaskManager();
            if (!taskManagereOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample TaskManager");
            }
            initialTaskManagerData = taskManagereOptional.orElseGet(SampleDataUtil::getSampleTaskManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TaskManager");
            initialTaskManagerData = new TaskManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskManager");
            initialTaskManagerData = new TaskManager();
        }

        initialFloatingTaskManagerData = new FloatingTaskManager();
        initialEventManagerData = new EventManager();
        return new ModelManager(initialTaskManagerData, initialFloatingTaskManagerData, initialEventManagerData,
                userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        // Update config file in case it was missing to begin with or there are
        // new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    protected UserPrefs initPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = this.storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskManager");
            initializedPrefs = new UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are
        // new/unused fields
        try {
            this.storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting TaskManager " + MainApp.VERSION);
        this.ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Task Manager ] =============================");
        this.ui.stop();
        try {
            this.storage.saveUserPrefs(this.userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }
}
