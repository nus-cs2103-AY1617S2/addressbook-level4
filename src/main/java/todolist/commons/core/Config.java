package todolist.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "To-Do List App";
    private Level logLevel = Level.INFO;
    private static String userPrefsFilePath = "preferences.json";
    private static String todoListFilePath = "data/todolist.xml";
    private String todoListName = "MyToDoList";


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

    public static String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public static void setUserPrefsFilePath(String userPrefsFilePath) {
        Config.userPrefsFilePath = userPrefsFilePath;
    }

    public static String getToDoListFilePath() {
        return todoListFilePath;
    }

    public static void setToDoListFilePath(String todoListFilePath) {
        Config.todoListFilePath = todoListFilePath;
    }

    public String getToDoListBookName() {
        return todoListName;
    }

    public void setToDoListName(String todoListName) {
        this.todoListName = todoListName;
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
//              && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
//              && Objects.equals(todoListFilePath, o.todoListFilePath)
                && Objects.equals(todoListName, o.todoListName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, todoListFilePath, todoListName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + todoListFilePath);
        sb.append("\nToDoList name : " + todoListName);
        return sb.toString();
    }

}
