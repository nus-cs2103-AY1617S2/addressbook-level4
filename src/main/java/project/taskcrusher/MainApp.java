package project.taskcrusher;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import project.taskcrusher.commons.core.Config;
import project.taskcrusher.commons.core.EventsCenter;
import project.taskcrusher.commons.core.LogsCenter;
import project.taskcrusher.commons.core.Version;
import project.taskcrusher.commons.events.storage.LoadNewStorageFileEvent;
import project.taskcrusher.commons.events.ui.ExitAppRequestEvent;
import project.taskcrusher.commons.exceptions.DataConversionException;
import project.taskcrusher.commons.util.ConfigUtil;
import project.taskcrusher.commons.util.StringUtil;
import project.taskcrusher.logic.Logic;
import project.taskcrusher.logic.LogicManager;
import project.taskcrusher.model.Model;
import project.taskcrusher.model.ModelManager;
import project.taskcrusher.model.ReadOnlyUserInbox;
import project.taskcrusher.model.UserInbox;
import project.taskcrusher.model.UserPrefs;
import project.taskcrusher.model.util.SampleDataUtil;
import project.taskcrusher.storage.Storage;
import project.taskcrusher.storage.StorageManager;
import project.taskcrusher.ui.Ui;
import project.taskcrusher.ui.UiManager;

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
        logger.info("=============================[ Initializing Taskcrusher ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getUserInboxFilePath(), config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        return new ModelManager(loadInitialUserInboxFromStorage(storage), userPrefs);
    }

    private ReadOnlyUserInbox loadInitialUserInboxFromStorage(Storage newStorage) {
        Optional<ReadOnlyUserInbox> userInboxOptional;
        ReadOnlyUserInbox initialData;
        try {
            userInboxOptional = storage.readUserInbox();
            if (!userInboxOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample user inbox");
            }
            initialData = userInboxOptional.orElseGet(SampleDataUtil::getSampleUserInbox);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty user inbox");
            initialData = new UserInbox();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty user inbox");
            initialData = new UserInbox();
        }
        return initialData;
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
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. " +
                    "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
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
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
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
        logger.info("Starting Taskcrusher " + MainApp.VERSION);
        ui.start(primaryStage);
        model.signalUiForUpdatedlists(); //Post event here to refresh the list at the beginning
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Taskcrusher ] =============================");
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

    //@@author A0127737X
    @Subscribe
    public void handleLoadNewStorageFileEvent(LoadNewStorageFileEvent lnsfe) {
        logger.info("Attempting to change storage file to  " + lnsfe.filePathToLoad);
        String currentStorageFilePath = config.getUserInboxFilePath();
        try {
            setStoragePathInConfig(lnsfe.filePathToLoad);
        } catch (IOException e) {
            logger.warning(LoadNewStorageFileEvent.MESSAGE_LOAD_FAILED);
            config.setUserInboxFilePath(currentStorageFilePath); //set it back to old path
            return;
        }
        reinitialiseMainAppWithNewStorage(lnsfe.filePathToLoad);
        logger.info("New storage file successfully loaded");
    }

    private void setStoragePathInConfig(String newStorageFile) throws IOException {
        config.setUserInboxFilePath(newStorageFile);
        ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
    }

    private void reinitialiseMainAppWithNewStorage(String newStorageFile) {
        EventsCenter.getInstance().unregisterHandler(storage);
        storage = new StorageManager(newStorageFile, config.getUserPrefsFilePath());
        model.resetData(loadInitialUserInboxFromStorage(storage));
        logic = new LogicManager(model, storage);
        ui.setLogic(logic);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
