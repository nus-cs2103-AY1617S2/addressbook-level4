package seedu.todolist.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "ToDo App";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String toDoListFilePath = "data/todolist.xml";
    private String toDoListName = "MyToDoList";


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

    public String getToDoListFilePath() {
        return toDoListFilePath;
    }

    public void setToDoListFilePath(String toDoListFilePath) {
        this.toDoListFilePath = toDoListFilePath;
    }

    public String getToDoListName() {
        return toDoListName;
    }

    public void setToDoListName(String toDoListName) {
        this.toDoListName = toDoListName;
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
                && Objects.equals(toDoListFilePath, o.toDoListFilePath)
                && Objects.equals(toDoListName, o.toDoListName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, toDoListFilePath, toDoListName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + toDoListFilePath);
        sb.append("\nToDoList name : " + toDoListName);
        return sb.toString();
    }

}
