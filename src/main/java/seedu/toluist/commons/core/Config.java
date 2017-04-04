//@@author A0131125Y
package seedu.toluist.commons.core;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.toluist.commons.exceptions.DataConversionException;
import seedu.toluist.commons.util.JsonUtil;
import seedu.toluist.model.AliasTable;

/**
 * Config values used by the app
 * Since Config is largely a global state, singleton pattern can be largely applied here
 */
public class Config {
    private static final Logger logger = LogsCenter.getLogger(Config.class);
    public static final String DEFAULT_CONFIG_FILE_PATH = "data/config.json";
    public static final String DEFAULT_TODO_LIST_FILE_PATH = "data/todolist.json";
    public static final String APP_NAME = "ToLuist App";
    public static final String LOG_MESSAGE_SAVE_CONFIG_FAILURE = "Saving config failed";

    private static Config instance;
    private static String configFilePath = DEFAULT_CONFIG_FILE_PATH;

    // Config values customizable through config file
    private final String appTitle = APP_NAME;
    private Level logLevel = Level.INFO;
    private AliasTable aliasTable = new AliasTable();
    private String todoListFilePath = DEFAULT_TODO_LIST_FILE_PATH;
    private GuiSettings guiSettings = new GuiSettings();

    /**
     * Load config from disk
     * @return Config data
     */
    public static Config getInstance() {
        if (instance == null) {
            try {
                instance = JsonUtil.readJsonFile(configFilePath, Config.class).orElse(new Config());
            } catch (DataConversionException e) {
                instance = new Config();
            }
        }
        return instance;
    }

    /**
     * Save config to disk
     * @return true if successful, false otherwise
     */
    public boolean save() {
        try {
            JsonUtil.saveJsonFile(this, configFilePath);
            return true;
        } catch (IOException e) {
            logger.severe(LOG_MESSAGE_SAVE_CONFIG_FAILURE);
            return false;
        }
    }

    public static void setConfigFilePath(String configFilePath) {
        Config.configFilePath = configFilePath;
    }

    public void setTodoListFilePath(String todoListFilePath) {
        this.todoListFilePath = todoListFilePath;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public String getTodoListFilePath() {
        return todoListFilePath;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public AliasTable getAliasTable() {
        return aliasTable;
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Config // instanceof handles nulls
                && appTitle.equals(((Config) other).appTitle)
                && logLevel.equals(((Config) other).logLevel)
                && todoListFilePath.equals(((Config) other).todoListFilePath)
                && aliasTable.equals(((Config) other).aliasTable)
                && guiSettings.equals(((Config) other).guiSettings));
    }
}
