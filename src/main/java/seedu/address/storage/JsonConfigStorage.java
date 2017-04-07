//@@author A0148052L

package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;

public class JsonConfigStorage implements ConfigStorage {
    private String filePath;
    public JsonConfigStorage(String configFilePath) {
        filePath = configFilePath;
    }

    @Override
      public Config readConfig() throws DataConversionException, IOException {
        Optional<Config> tryConfig = ConfigUtil.readConfig(filePath);
        if (tryConfig.isPresent()) {
            return tryConfig.get();
        } else {
            throw new IOException("File Path invalid");
        }
    }

    @Override
      public void saveConfig(Config config) throws IOException {
        ConfigUtil.saveConfig(config, filePath);
    }
}
