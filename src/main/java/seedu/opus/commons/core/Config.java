package seedu.opus.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";
    public static final String DEFAULT_USER_PREFS_FILE = "preferences.json";
    public static final String DEFAULT_SAVE_LOCATION = "data/opus.xml";

    // Config values customizable through config file
    private String appTitle = "Opus";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = DEFAULT_USER_PREFS_FILE;
    private String taskManagerFilePath = DEFAULT_SAVE_LOCATION;
    private String configFilePath = DEFAULT_CONFIG_FILE;
    private String appName = "Opus";


    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    public String getTaskManagerFilePath() {
        return taskManagerFilePath;
    }

    public void setTaskManagerFilePath(String taskManagerFilePath) {
        this.taskManagerFilePath = taskManagerFilePath;
    }

    //@@author A0148081H
    public String getConfigFilePath() {
        return configFilePath;
    }

    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }
    //@@author

    public String getTaskManagerName() {
        return appName;
    }

    public void setTaskManagerName(String taskManagerName) {
        this.appName = taskManagerName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Config)) { //this handles null as well.
            return false;
        }

        Config o = (Config) other;

        return Objects.equals(appTitle, o.appTitle)
                && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(taskManagerFilePath, o.taskManagerFilePath)
                && Objects.equals(appName, o.appName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, taskManagerFilePath, appName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + taskManagerFilePath);
        sb.append("\nApp name : " + appName);
        return sb.toString();
    }

}
