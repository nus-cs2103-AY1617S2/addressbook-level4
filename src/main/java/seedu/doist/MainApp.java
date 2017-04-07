package seedu.doist;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.doist.commons.core.Config;
import seedu.doist.commons.core.EventsCenter;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.core.Version;
import seedu.doist.commons.events.ui.ExitAppRequestEvent;
import seedu.doist.commons.exceptions.DataConversionException;
import seedu.doist.commons.util.ConfigUtil;
import seedu.doist.commons.util.StringUtil;
import seedu.doist.logic.Logic;
import seedu.doist.logic.LogicManager;
import seedu.doist.model.AliasListMap;
import seedu.doist.model.ConfigManager;
import seedu.doist.model.ConfigModel;
import seedu.doist.model.Model;
import seedu.doist.model.ModelManager;
import seedu.doist.model.ReadOnlyAliasListMap;
import seedu.doist.model.ReadOnlyTodoList;
import seedu.doist.model.TodoList;
import seedu.doist.model.UserPrefs;
import seedu.doist.model.util.SampleDataUtil;
import seedu.doist.storage.Storage;
import seedu.doist.storage.StorageManager;
import seedu.doist.ui.Ui;
import seedu.doist.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    protected static final Logger LOGGER = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);

    private static final String APPLICATION_NAME = "Doist";

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected ConfigModel configModel;
    protected UserPrefs userPrefs;


    @Override
    public void init() throws Exception {
        LOGGER.info("=========================[ Initializing " + APPLICATION_NAME + " ]=======================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        configModel = new ConfigManager(config);

        storage = new StorageManager(config.getAbsoluteTodoListFilePath(), config.getAbsoluteAliasListMapFilePath(),
                                        config.getAbsoluteUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, configModel, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    protected String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        ReadOnlyTodoList initialData = initTodoListData(storage);
        ReadOnlyAliasListMap initialAliasData = initAliasListMapData(storage);

        return new ModelManager(initialData, initialAliasData, userPrefs);
    }

    protected static ReadOnlyTodoList initTodoListData(Storage storage) {
        Optional<ReadOnlyTodoList> todoListOptional;
        ReadOnlyTodoList initialData;
        try {
            todoListOptional = storage.readTodoList();
            if (!todoListOptional.isPresent()) {
                LOGGER.info("Data file not found. Will be starting with a sample To-do List");
            }
            initialData = todoListOptional.orElseGet(SampleDataUtil::getSampleTodoList);
        } catch (DataConversionException e) {
            LOGGER.warning("Data file not in the correct format. Will be starting with an empty To-do List");
            initialData = new TodoList();
        } catch (IOException e) {
            LOGGER.warning("Problem while reading from the file. Will be starting with an empty To-do List");
            initialData = new TodoList();
        }
        return initialData;
    }

    protected static ReadOnlyAliasListMap initAliasListMapData(Storage storage) {
        Optional<ReadOnlyAliasListMap> aliasListMapOptional;
        ReadOnlyAliasListMap initialAliasData;
        try {
            aliasListMapOptional = storage.readAliasListMap();
            if (!aliasListMapOptional.isPresent()) {
                LOGGER.info("Data file not found. Will be starting with the default alias list map");
                initialAliasData = new AliasListMap();
            } else {
                initialAliasData = aliasListMapOptional.get();
            }
        } catch (DataConversionException e) {
            LOGGER.warning("Data file not in the correct format. Will be starting with the default alias list map");
            initialAliasData = new AliasListMap();
        } catch (IOException e) {
            LOGGER.warning("Problem while reading from the file. Will be starting with the default alias list map");
            initialAliasData = new AliasListMap();
        }
        return initialAliasData;
    }

    protected void initLogging(Config config) {
        LogsCenter.init(config);
    }

    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            LOGGER.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        LOGGER.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            LOGGER.warning("Config file at " + configFilePathUsed + " is not in the correct format. " +
                    "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            LOGGER.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    protected UserPrefs initPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        LOGGER.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            LOGGER.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            LOGGER.warning("Problem while reading from the file. Will be starting with an empty To-do List");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            LOGGER.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    protected void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        LOGGER.info("Starting " + APPLICATION_NAME + " " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        LOGGER.info("============================ [ Stopping " + APPLICATION_NAME + " ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            LOGGER.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        LOGGER.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
