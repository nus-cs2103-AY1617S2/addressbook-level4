package seedu.doit.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Doit";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String taskManagerFilePath = "data/taskmanager.xml";
    private String taskManagerName = "MyTaskManager";

    public String getAppTitle() {
        return this.appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Level getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getUserPrefsFilePath() {
        return this.userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    public String getTaskManagerFilePath() {
        return this.taskManagerFilePath;
    }

    public void setTaskManagerFilePath(String taskManagerFilePath) {
        this.taskManagerFilePath = taskManagerFilePath;
    }

    public String getTaskManagerName() {
        return this.taskManagerName;
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

        return Objects.equals(this.appTitle, o.appTitle) && Objects.equals(this.logLevel, o.logLevel)
                && Objects.equals(this.userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(this.taskManagerFilePath, o.taskManagerFilePath)
                && Objects.equals(this.taskManagerName, o.taskManagerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.appTitle, this.logLevel, this.userPrefsFilePath, this.taskManagerFilePath,
                this.taskManagerName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + this.appTitle);
        sb.append("\nCurrent log level : " + this.logLevel);
        sb.append("\nPreference file Location : " + this.userPrefsFilePath);
        sb.append("\nLocal data file location : " + this.taskManagerFilePath);
        sb.append("\nTaskManager name : " + this.taskManagerName);
        return sb.toString();
    }

}
