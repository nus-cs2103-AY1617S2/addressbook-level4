package seedu.whatsleft.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "WhatsLeft App";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String whatsLeftFilePath = "data/whatsleft.xml";
    private String whatsLeftName = "MyWhatsLeft";


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

    public String getWhatsLeftFilePath() {
        return whatsLeftFilePath;
    }

    public void setWhatsLeftFilePath(String whatsLeftFilePath) {
        this.whatsLeftFilePath = whatsLeftFilePath;
    }

    public String getWhatsLeftName() {
        return whatsLeftName;
    }

    public void setWhatsLeftName(String whatsLeftName) {
        this.whatsLeftName = whatsLeftName;
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
                && Objects.equals(whatsLeftFilePath, o.whatsLeftFilePath)
                && Objects.equals(whatsLeftName, o.whatsLeftName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, whatsLeftFilePath, whatsLeftName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + whatsLeftFilePath);
        sb.append("\nWhatsLeft name : " + whatsLeftName);
        return sb.toString();
    }

}
