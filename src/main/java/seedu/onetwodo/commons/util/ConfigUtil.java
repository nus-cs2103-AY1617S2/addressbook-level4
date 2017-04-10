package seedu.onetwodo.commons.util;

import java.io.IOException;
import java.util.Optional;

import seedu.onetwodo.commons.core.Config;
import seedu.onetwodo.commons.exceptions.DataConversionException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    public static Optional<Config> readConfig(String configFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }
//@@author A0139343E
    public static void saveConfig(Config config, String configFilePath) throws IOException {
        if (!config.getAppTitle().equals("Test App")) {
            JsonUtil.saveJsonFile(config, configFilePath);
        }
    }

}
