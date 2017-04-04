package seedu.doist.commons.core;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

//@@author A0140887W
/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";
    public static String lastUsedFile = "";

    // Config values customizable through config file
    private String appTitle = "Doist";
    private Level logLevel = Level.INFO;
    private String absoluteStoragePath = "";
    private String userPrefsFilePath = "preferences.json";
    private String todoListFilePath = "data" + File.separator + "todolist.xml";
    private String aliasListMapFilePath = "data" + File.separator + "aliaslistmap.xml";
    private String todoListName = "MyTodoList";

    public Config() {
        // Gets current working directory of Doist (in operating system platform-specific style)
        absoluteStoragePath = Paths.get(".").toAbsolutePath().normalize().toString();
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

    public String getAbsoluteStoragePath() {
        return absoluteStoragePath;
    }

    /** Model should call this to save the absolute storage path */
    public void setAbsoluteStoragePath(String absoluteStoragePath) {
        this.absoluteStoragePath = absoluteStoragePath;
    }

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public String getAbsoluteUserPrefsFilePath() {
        return absoluteStoragePath + File.separator + userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    public String getTodoListFilePath() {
        return todoListFilePath;
    }

    public String getAbsoluteTodoListFilePath() {
        return absoluteStoragePath + File.separator + todoListFilePath;
    }

    public void setTodoListFilePath(String todoListFilePath) {
        this.todoListFilePath = todoListFilePath;
    }

    public String getAliasListMapFilePath() {
        return aliasListMapFilePath;
    }

    public String getAbsoluteAliasListMapFilePath() {
        return absoluteStoragePath + File.separator + aliasListMapFilePath;
    }

    public void setAliasListMapFilePath(String aliasListMapFilePath) {
        this.aliasListMapFilePath = aliasListMapFilePath;
    }

    public String getTodoListName() {
        return todoListName;
    }

    public void setTodoListName(String todoListName) {
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
                && Objects.equals(absoluteStoragePath, o.absoluteStoragePath)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(aliasListMapFilePath, o.aliasListMapFilePath)
                && Objects.equals(todoListFilePath, o.todoListFilePath)
                && Objects.equals(todoListName, o.todoListName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, absoluteStoragePath, userPrefsFilePath,
                aliasListMapFilePath, todoListFilePath, todoListName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nAbsolute Storage Path : " + absoluteStoragePath);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nAlias List Map file location : " + aliasListMapFilePath);
        sb.append("\nLocal data file location : " + todoListFilePath);
        sb.append("\nTodoList name : " + todoListName);
        return sb.toString();
    }

}
