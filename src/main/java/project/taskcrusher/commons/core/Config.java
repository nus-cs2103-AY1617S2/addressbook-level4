package project.taskcrusher.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file. These are default
    private String appTitle = "TaskCrusher";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String userInboxFilePath = "data/userInbox.xml";
    private String userInboxName = "MyTaskCrusher";


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

    public String getUserInboxFilePath() {
        return userInboxFilePath;
    }

    public void setUserInboxFilePath(String userInbox) {
        this.userInboxFilePath = userInbox;
    }

    public String getUserInboxName() {
        return userInboxName;
    }

    public void setUserInboxName(String userInbox) {
        this.userInboxName = userInbox;
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
                && Objects.equals(userInboxFilePath, o.userInboxFilePath)
                && Objects.equals(userInboxName, o.userInboxName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, userInboxFilePath, userInboxName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + userInboxFilePath);
        sb.append("\nuserInbox name : " + userInboxName);
        return sb.toString();
    }

}
