package seedu.ezdo.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "EzDo";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String ezDoFilePath = "data/ezDo.xml";
    private String ezDoName = "ezDo";


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

    public String getEzDoFilePath() {
        return ezDoFilePath;
    }

    public void setEzDoFilePath(String ezDoFilePath) {
        this.ezDoFilePath = ezDoFilePath;
    }

    public String getEzDoName() {
        return ezDoName;
    }

    public void setEzDoName(String ezDoName) {
        this.ezDoName = ezDoName;
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
                && Objects.equals(ezDoFilePath, o.ezDoFilePath)
                && Objects.equals(ezDoName, o.ezDoName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, ezDoFilePath, ezDoName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + ezDoFilePath);
        sb.append("\nEzDo name : " + ezDoName);
        return sb.toString();
    }

}
