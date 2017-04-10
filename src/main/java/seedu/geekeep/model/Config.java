package seedu.geekeep.model;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "GeeKeep - Command Line Featured Task Manager";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String geeKeepFilePath = "data/geekeep.xml";
    private String geeKeepName = "MyGeeKeep";


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

    public String getGeekeepFilePath() {
        return geeKeepFilePath;
    }

    public void setGeeKeepFilePath(String geeKeepFilePath) {
        this.geeKeepFilePath = geeKeepFilePath;
    }

    public String getGeeKeepName() {
        return geeKeepName;
    }

    public void setGeeKeepName(String geeKeepName) {
        this.geeKeepName = geeKeepName;
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
                && Objects.equals(geeKeepFilePath, o.geeKeepFilePath)
                && Objects.equals(geeKeepName, o.geeKeepName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, geeKeepFilePath, geeKeepName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + geeKeepFilePath);
        sb.append("\nGeeKeep name : " + geeKeepName);
        return sb.toString();
    }

}
