package seedu.geekeep;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.geekeep.commons.core.EventsCenter;
import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.commons.core.Version;
import seedu.geekeep.commons.events.ui.ExitAppRequestEvent;
import seedu.geekeep.commons.exceptions.DataConversionException;
import seedu.geekeep.commons.util.StringUtil;
import seedu.geekeep.logic.Logic;
import seedu.geekeep.logic.LogicManager;
import seedu.geekeep.model.Config;
import seedu.geekeep.model.GeeKeep;
import seedu.geekeep.model.Model;
import seedu.geekeep.model.ModelManager;
import seedu.geekeep.model.ReadOnlyGeeKeep;
import seedu.geekeep.model.UserPrefs;
import seedu.geekeep.model.util.SampleDataUtil;
import seedu.geekeep.storage.ConfigStorage;
import seedu.geekeep.storage.JsonConfigStorage;
import seedu.geekeep.storage.Storage;
import seedu.geekeep.storage.StorageManager;
import seedu.geekeep.ui.Ui;
import seedu.geekeep.ui.UiManager;

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


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing GeeKeep ]===========================");
        super.init();

        String configFilePath = getApplicationParameter("config");
        config = initConfig(configFilePath);
        if (configFilePath == null) {
            configFilePath = Config.DEFAULT_CONFIG_FILE;
        }
        storage = new StorageManager(configFilePath, config.getGeekeepFilePath(), config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(config, storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        if (getParameters() == null) {
            return null;
        } else {
            Map<String, String> applicationParameters = getParameters().getNamed();
            return applicationParameters.get(parameterName);
        }
    }

    private Model initModelManager(Config config, Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyGeeKeep> geeKeepOptional;
        ReadOnlyGeeKeep initialData;
        try {
            geeKeepOptional = storage.readGeeKeep();
            if (!geeKeepOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample GeeKeep");
            }
            initialData = geeKeepOptional.orElseGet(SampleDataUtil::getSampleGeeKeep);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty GeeKeep");
            initialData = new GeeKeep();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty GeeKeep");
            initialData = new GeeKeep();
        }

        return new ModelManager(config, initialData, userPrefs);
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

        ConfigStorage configStorage = new JsonConfigStorage(configFilePathUsed);
        try {
            Optional<Config> configOptional = configStorage.readConfig();
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. " +
                    "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            configStorage.saveConfig(initializedConfig);
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
        logger.info("Starting GeeKeep " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping GeeKeep ] =============================");
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

    public static void main(String[] args) {
        launch(args);
    }
}
