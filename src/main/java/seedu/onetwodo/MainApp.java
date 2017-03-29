package seedu.onetwodo;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.onetwodo.commons.core.Config;
import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.commons.core.Version;
import seedu.onetwodo.commons.events.ui.ExitAppRequestEvent;
import seedu.onetwodo.commons.exceptions.DataConversionException;
import seedu.onetwodo.commons.util.ConfigUtil;
import seedu.onetwodo.commons.util.StringUtil;
import seedu.onetwodo.logic.Logic;
import seedu.onetwodo.logic.LogicManager;
import seedu.onetwodo.model.Model;
import seedu.onetwodo.model.ModelManager;
import seedu.onetwodo.model.ReadOnlyToDoList;
import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.model.UserPrefs;
import seedu.onetwodo.model.util.SampleDataUtil;
import seedu.onetwodo.storage.Storage;
import seedu.onetwodo.storage.StorageManager;
import seedu.onetwodo.ui.Ui;
import seedu.onetwodo.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);
    private static MainApp instance;

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    public static MainApp getInstance() {
        if (instance == null) {
            instance = new MainApp();
        }
        return instance;
    }

    @Override
    public void init() throws Exception {
        MainApp instance = MainApp.getInstance();
        logger.info("=============================[ Initializing ToDoList ]===========================");
        super.init();

        
        instance.config = initConfig(getApplicationParameter("config"));
        instance.storage = new StorageManager(instance.config.getToDoListFilePath(),
                instance.config.getUserPrefsFilePath());

        instance.userPrefs = initPrefs(instance.config);

        initLogging(instance.config);

        instance.model = initModelManager(instance.storage, instance.userPrefs);

        instance.logic = new LogicManager(instance.model, instance.storage);

        instance.ui = new UiManager(instance.logic, instance.config, instance.userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    public Config getConfig() {
        return MainApp.getInstance().config;
    }

    public Storage getStorage() {
        return MainApp.getInstance().storage;
    }

    public Model getModel() {
        return MainApp.getInstance().model;
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyToDoList> toDoOptional;
        ReadOnlyToDoList initialData;
        try {
            toDoOptional = MainApp.getInstance().storage.readToDoList();
            if (!toDoOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ToDoList");
            }
            initialData = toDoOptional.orElseGet(SampleDataUtil::getSampleToDoList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ToDoList");
            initialData = new ToDoList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ToDoList");
            initialData = new ToDoList();
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
        assert MainApp.getInstance().config != null;

        String prefsFilePath = MainApp.getInstance().config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);
        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = MainApp.getInstance().storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ToDoList");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            MainApp.getInstance().storage.saveUserPrefs(initializedPrefs);
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
        logger.info("Starting ToDoList " + MainApp.VERSION);
        MainApp.getInstance().ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping ToDoList ] =============================");
        MainApp.getInstance().ui.stop();
        try {
            MainApp.getInstance().storage.saveUserPrefs(MainApp.getInstance().userPrefs);
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
        MainApp.getInstance();
        launch(args);
    }
}
