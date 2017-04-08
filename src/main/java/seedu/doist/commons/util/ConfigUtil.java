package seedu.doist.commons.util;

import java.io.IOException;
import java.util.Optional;

import seedu.doist.commons.core.Config;
import seedu.doist.commons.exceptions.DataConversionException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    public static Optional<Config> readConfig(String configFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    public static void saveConfig(Config config, String configFilePath) throws IOException {
        JsonUtil.saveJsonFile(config, configFilePath);
        Config.lastUsedFile = configFilePath;
    }

    //@@author A0140887W
    public static String getConfigPath() throws IOException {
        if (!Config.lastUsedFile.isEmpty()) {
            return Config.lastUsedFile;
        } else {
            return Config.DEFAULT_CONFIG_FILE;
        }
    }

}
