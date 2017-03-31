package savvytodo;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import savvytodo.commons.core.Config;
import savvytodo.commons.core.EventsCenter;
import savvytodo.commons.core.LogsCenter;
import savvytodo.commons.core.Version;
import savvytodo.commons.events.storage.LoadStorageFileEvent;
import savvytodo.commons.events.ui.ExitAppRequestEvent;
import savvytodo.commons.exceptions.DataConversionException;
import savvytodo.commons.util.ConfigUtil;
import savvytodo.commons.util.StringUtil;
import savvytodo.logic.Logic;
import savvytodo.logic.LogicManager;
import savvytodo.model.Model;
import savvytodo.model.ModelManager;
import savvytodo.model.ReadOnlyTaskManager;
import savvytodo.model.TaskManager;
import savvytodo.model.UserPrefs;
import savvytodo.model.util.SampleDataUtil;
import savvytodo.storage.Storage;
import savvytodo.storage.StorageManager;
import savvytodo.ui.Ui;
import savvytodo.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    public String configFile = Config.DEFAULT_CONFIG_FILE;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Task Manager ]===========================");
        super.init();

        initEventsCenter();

        initApplicationFromConfig(getApplicationParameter("config"), true);
    }

    //@@author A0140036X
    /**
     * Starts application. Updates UI with new logic and storage if UI already exists.
     * @author A0140036X
     * @param configFilePath File path of json file containing configurations
     * @param useSampleDataIfStorageFileNotFound If true, sample data is to be used if storage file is not found.
     * If false, an empty task manager will be created.
     */
    public void initApplicationFromConfig(String configFilePath, boolean useSampleDataIfStorageFileNotFound) {
        config = initConfig(configFilePath);

        storage = new StorageManager(config.getTaskManagerFilePath(), config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs, useSampleDataIfStorageFileNotFound ? null : new TaskManager());

        logic = new LogicManager(model, storage);

        if (ui == null) {
            ui = new UiManager(logic, config, userPrefs);
        } else {
            ui.setLogic(logic);
        }
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    //@@author A0140036X
    /**
     * Initializes model based on storage. If storage file is not found, default task manager provided will be used.
     * If task manager is null, sample task manager will be created.
     * @param storage
     * @param userPrefs
     * @param defaultTaskManager
     * @return
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs, TaskManager defaultTaskManager) {
        Optional<ReadOnlyTaskManager> taskManagerOptional;
        ReadOnlyTaskManager initialData;
        try {
            taskManagerOptional = storage.readTaskManager();
            if (!taskManagerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with "
                        + ((defaultTaskManager == null ? "a sample " : "provided ") + "TaskManager"));
            }
            logger.info("Data file found " + storage.getTaskManagerFilePath());
            initialData = taskManagerOptional.orElseGet(
                    defaultTaskManager == null ? SampleDataUtil::getSampleTaskManager : () -> new TaskManager());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TaskManager");
            initialData = new TaskManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskManager");
            initialData = new TaskManager();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    protected Config initConfig(String configFilePath) {
        Config initializedConfig;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFile = configFilePath;
        }

        logger.info("Using config file : " + configFile);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFile);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFile + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFile);
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
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskManager");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save preference file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting TaskManager " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Task Manager ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
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

    /**
     * Loads a new task manager file.
     * 1. Update and save config file with new storage file path
     * 2. Update UI with new logic
     * 3. Save task manager into new file
     * @author A0140036X
     */
    @Subscribe
    public void handleLoadStorageFileEvent(LoadStorageFileEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        String taskManagerFilePath = event.getFilePath();
        config.setTaskManagerFilePath(taskManagerFilePath);

        try {
            ConfigUtil.saveConfig(config, configFile);
        } catch (IOException e) {
            logger.severe("Failed to save config " + StringUtil.getDetails(e));
            this.stop();
        }

        logger.info("Setting UI with new logic");
        initApplicationFromConfig(configFile, false);

        try {
            storage.saveTaskManager(model.getTaskManager());
        } catch (IOException e) {
            logger.severe("Failed to save task manager " + StringUtil.getDetails(e));
            this.stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
