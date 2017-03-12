package seedu.toluist.commons.core;

import seedu.toluist.commons.exceptions.DataConversionException;
import seedu.toluist.commons.util.JsonUtil;
import seedu.toluist.model.CommandAliasConfig;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE_PATH = "data/config.json";
    public static final String DEFAULT_TODO_LIST_FILE_PATH = "data/todolist.json";

    private static final Logger logger = LogsCenter.getLogger(Config.class);
    private static String configFilePath = DEFAULT_CONFIG_FILE_PATH;

    // Config values customizable through config file
    private final String appTitle = "ToLuist App";
    private Level logLevel = Level.INFO;
    private CommandAliasConfig commandAliasConfig = new CommandAliasConfig();
    private String todoListFilePath = DEFAULT_TODO_LIST_FILE_PATH;

    /**
     * Load config from disk
     * @return Config data
     */
    public static Config load() {
        try {
            Optional<Config> configOptional = JsonUtil.readJsonFile(configFilePath, Config.class);
            return configOptional.isPresent() ? configOptional.get() : new Config();
        } catch (DataConversionException e) {
            logger.severe("Loading config failed");
            return new Config();
        }
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
            logger.severe("Saving config failed");
            return false;
        }
    }

    public static void setConfigFilePath(String configFilePath) {
        Config.configFilePath = configFilePath;
    }

    public void setTodoListFilePath(String todoListFilePath) {
        this.todoListFilePath = todoListFilePath;
    }

    public String getTodoListFilePath() {
        return todoListFilePath;
    }

    public String getConfigFilePath() {
        return configFilePath;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public CommandAliasConfig getCommandAliasConfig() {
        return commandAliasConfig;
    }
}
