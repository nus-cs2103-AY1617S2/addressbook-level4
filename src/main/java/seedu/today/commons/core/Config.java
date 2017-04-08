package seedu.today.commons.core;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

import seedu.today.commons.util.FileUtil;
import seedu.today.commons.util.JsonUtil;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";
    private String filePath;

    // Config values customizable through config file
    private String appTitle = "Task Manager";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String taskManagerFilePath = "data/taskmanager.xml";
    private String taskManagerName = "MyTaskManager";

    public Config() {
        this.filePath = DEFAULT_CONFIG_FILE;
    }

    // Initialized with a specified filepath
    public Config(String filePath) {
        if (FileUtil.isWritable(filePath)) {
            this.filePath = filePath;
        } else {
            this.filePath = DEFAULT_CONFIG_FILE;
        }
    }

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

    public String getTaskManagerName() {
        return taskManagerName;
    }

    public void setTaskManagerName(String taskManagerName) {
        this.taskManagerName = taskManagerName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Config)) { // this handles null as well.
            return false;
        }

        Config o = (Config) other;

        return Objects.equals(appTitle, o.appTitle) && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(taskManagerFilePath, o.taskManagerFilePath)
                && Objects.equals(taskManagerName, o.taskManagerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, taskManagerFilePath, taskManagerName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + taskManagerFilePath);
        sb.append("\nTaskManager name : " + taskManagerName);
        return sb.toString();
    }

    public void setFilePath(String newFilePath) {
        this.filePath = newFilePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void save() throws IOException {
        JsonUtil.saveJsonFile(this, filePath);
    }

}
