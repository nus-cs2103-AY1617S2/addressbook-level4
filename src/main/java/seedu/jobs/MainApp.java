package seedu.jobs;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.jobs.commons.core.Config;
import seedu.jobs.commons.core.EventsCenter;
import seedu.jobs.commons.core.LogsCenter;
import seedu.jobs.commons.core.Version;
import seedu.jobs.commons.events.storage.SaveLoginInfoEvent;
import seedu.jobs.commons.events.ui.CalendarDisplayEvent;
import seedu.jobs.commons.events.ui.ExitAppRequestEvent;
import seedu.jobs.commons.events.ui.TaskBookReloadEvent;
import seedu.jobs.commons.exceptions.DataConversionException;
import seedu.jobs.commons.util.ConfigUtil;
import seedu.jobs.commons.util.StringUtil;
import seedu.jobs.logic.Logic;
import seedu.jobs.logic.LogicManager;
import seedu.jobs.logic.calendar.CalendarManager;
import seedu.jobs.model.LoginInfo;
import seedu.jobs.model.Model;
import seedu.jobs.model.ModelManager;
import seedu.jobs.model.ReadOnlyTaskBook;
import seedu.jobs.model.TaskBook;
import seedu.jobs.model.UserPrefs;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;
import seedu.jobs.model.util.SampleDataUtil;
import seedu.jobs.storage.Storage;
import seedu.jobs.storage.StorageManager;
import seedu.jobs.ui.Ui;
import seedu.jobs.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(5, 0, 0, true);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;
    protected LoginInfo loginInfo;
    protected CalendarManager calendarManager;



    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing JOBS ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getTaskBookFilePath(), config.getUserPrefsFilePath(),
                config.getLoginInfoFilePath());

        userPrefs = initPrefs(config);
        loginInfo = initLoginInfo(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs, loginInfo);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) throws IllegalTimeException {
        Optional<ReadOnlyTaskBook> taskBookOptional;
        ReadOnlyTaskBook initialData;
        try {
            taskBookOptional = storage.readTaskBook();
            if (!taskBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = taskBookOptional.orElseGet(SampleDataUtil::getSampleTaskBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TaskBook");
            initialData = new TaskBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskBook");
            initialData = new TaskBook();
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
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskBook");
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


    protected LoginInfo initLoginInfo(Config config) {
        assert config != null;

        String loginInfoFilePath = config.getLoginInfoFilePath();
        logger.info("Using login file : " + loginInfoFilePath);

        LoginInfo initializedLoginInfo;
        try {
            Optional<LoginInfo> loginInfoOptional = storage.readLoginInfo();
            initializedLoginInfo = loginInfoOptional.orElse(new LoginInfo());
        } catch (DataConversionException e) {
            logger.warning("LoginInfo file at " + loginInfoFilePath + " is not in the correct format. " +
                    "Using default login info");
            initializedLoginInfo = new LoginInfo();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty LoginInfo");
            initializedLoginInfo = new LoginInfo();
        }

        //Update loginInfo file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveLoginInfo(initializedLoginInfo);;
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedLoginInfo;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting JOBS " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping JOBS ] =============================");
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

    @Subscribe
    public void handleCalendarDisplayEvent(CalendarDisplayEvent event) throws IllegalTimeException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.calendarManager = new CalendarManager();
        calendarManager.initCalendar(model.getFilteredTaskList());
    }

    @Subscribe
    public void handleSaveLoginInfoEvent(SaveLoginInfoEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        storage.saveLoginInfo(loginInfo);
    }

    @Subscribe
    public void handleTaskBookReloadEvent(TaskBookReloadEvent event) throws IOException, IllegalTimeException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        model = initModelManager(storage, userPrefs);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
