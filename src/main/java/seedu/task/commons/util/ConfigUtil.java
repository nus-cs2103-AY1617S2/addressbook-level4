package seedu.task.commons.util;

import java.io.IOException;
import java.util.Optional;

import seedu.task.commons.core.Config;
import seedu.task.commons.exceptions.DataConversionException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    public static Optional<Config> readConfig(String configFilePath) throws DataConversionException {
        System.out.println("reading " + configFilePath);
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    public static void saveConfig(Config config, String configFilePath) throws IOException {
        System.out.println("saving " + configFilePath);
        JsonUtil.saveJsonFile(config, configFilePath);
    }

}
