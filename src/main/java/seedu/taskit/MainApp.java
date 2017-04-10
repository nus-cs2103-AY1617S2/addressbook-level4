package seedu.taskit;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.taskit.logic.commands.ChangePathCommand;
import seedu.taskit.commons.core.Config;
import seedu.taskit.commons.core.EventsCenter;
import seedu.taskit.commons.core.LogsCenter;
import seedu.taskit.commons.core.Version;
import seedu.taskit.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskit.commons.events.storage.StorageFilePathChangedEvent;
import seedu.taskit.commons.events.ui.ExitAppRequestEvent;
import seedu.taskit.commons.exceptions.DataConversionException;
import seedu.taskit.commons.util.ConfigUtil;
import seedu.taskit.commons.util.StringUtil;
import seedu.taskit.logic.Logic;
import seedu.taskit.logic.LogicManager;
import seedu.taskit.logic.commands.ChangePathCommand;
import seedu.taskit.model.TaskManager;
import seedu.taskit.model.Model;
import seedu.taskit.model.ModelManager;
import seedu.taskit.model.ReadOnlyTaskManager;
import seedu.taskit.model.UserPrefs;
import seedu.taskit.model.util.SampleDataUtil;
import seedu.taskit.storage.Storage;
import seedu.taskit.storage.StorageManager;
import seedu.taskit.ui.Ui;
import seedu.taskit.ui.UiManager;

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
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getAddressBookFilePath(), config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();

        ChangePathCommand.setConfig(config);
        ChangePathCommand.setStorage(storage);
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyTaskManager> addressBookOptional;
        ReadOnlyTaskManager initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new TaskManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new TaskManager();
        }

        return new ModelManager(initialData, userPrefs);
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
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
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

    //@@author A0141011J
    @Subscribe
    public void handleStorageChangedEvent(StorageFilePathChangedEvent event) throws DataConversionException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        //set the new file path in config
        config.setAddressBookFilePath(event.getPath());

        try {
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);

            ReadOnlyTaskManager currentTaskManager = model.getAddressBook();

            //reset the storage object to the new file path and save the current task manager into it
            storage = new StorageManager(config.getAddressBookFilePath(), config.getUserPrefsFilePath());
            storage.saveAddressBook(currentTaskManager);
        } catch (IOException ioe) {
            EventsCenter.getInstance().post(new DataSavingExceptionEvent(ioe));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
