package t15b1.taskcrusher.commons.util;

import java.io.IOException;
import java.util.Optional;

import t15b1.taskcrusher.commons.core.Config;
import t15b1.taskcrusher.commons.exceptions.DataConversionException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    public static Optional<Config> readConfig(String configFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    public static void saveConfig(Config config, String configFilePath) throws IOException {
        JsonUtil.saveJsonFile(config, configFilePath);
    }

}
