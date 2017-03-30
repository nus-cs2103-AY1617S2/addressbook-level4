package seedu.address;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.events.storage.FileStorageChangedEvent;
import seedu.address.commons.events.storage.ForceSaveEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.undo.UndoManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

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
    protected String configPath;
    protected UserPrefs userPrefs;


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing DoOrDie ]===========================");
        super.init();

        loadFonts();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getTaskManagerFilePath(), config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs, model);

        initEventsCenter();
    }

    //@@author A0140042A
    /**
     * Load all fonts in the resources/fonts folder
     */
    private void loadFonts() {
        Font.loadFont(getClass().getResourceAsStream("/fonts/YouMurdererBB-Regular.otf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-Condensed.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto-Regular.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/fonts/FontAwesome-Regular.otf"), 10);
    }
    //@@author

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters;
        try {
            applicationParameters = getParameters().getNamed();
        } catch (Exception e) {
            //Initialize to empty if unable to get parameters
            applicationParameters = new ConcurrentHashMap<String, String>();
        }

        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyTaskManager> taskManagerOptional;
        ReadOnlyTaskManager initialData;
        try {
            taskManagerOptional = storage.readTaskManager();
            if (!taskManagerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample TaskManager");
            }
            initialData = taskManagerOptional.orElseGet(SampleDataUtil::getSampleTaskManager);
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

        configPath = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configPath = configFilePath;
        }

        logger.info("Using config file : " + configPath);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configPath);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configPath + " is not in the correct format. " +
                    "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configPath);
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
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskManager");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
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
        logger.info("Starting DoOrDie " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping DoOrDie ] =============================");
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

    //@@author A0140042A
    /**
     * Handles FileStorageChangedEvent to load the data from the new location
     * @throws Exception
     */
    @Subscribe
    public void handleFileStorageChangedEvent(FileStorageChangedEvent event) throws Exception {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        //Update & save the config file
        config.setTaskManagerFilePath(event.getFilePath());
        try {
            ConfigUtil.saveConfig(config, configPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Reinitialize components
        storage.setTaskManagerFilePath(event.getFilePath());
        model = initModelManager(storage, userPrefs);
        logic = new LogicManager(model, storage);
        //Set the Ui to the new logic & model since we don't want to destroy the old UI
        ui.setModel(model);
        ui.setLogic(logic);
        //Update UI to show all tasks since we have loaded the new Task Manager in
        model.updateFilteredListToShowAll();

        //Restart the undo manager
        UndoManager.getInstance().clear();

        //Save all current data into the new location
        storage.saveTaskManager(model.getTaskManager(), event.getFilePath());
    }

    /**
     * Handles ForceSaveEvent to save the config to a location
     * @throws Exception
     */
    @Subscribe
    public void handleForceSaveEvent(ForceSaveEvent event) throws Exception {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        storage.saveTaskManager(model.getTaskManager(), event.getFilePath());
    }
    //@@author

    public static void main(String[] args) {
        launch(args);
    }
}
