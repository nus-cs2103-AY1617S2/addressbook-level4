package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;

//@@author A0121668A
/**
 * A class to access UserConfig stored in the hard disk as a json file
 */
public class JsonUserConfigStorage implements UserConfigStorage {

    private String filePath;

    public JsonUserConfigStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<Config> readUserConfig() throws DataConversionException, IOException {
        return readUserConfig(filePath);
    }

    /**
     * Similar to {@link #readUserConfig()}
     * @param configFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<Config> readUserConfig(String configFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    @Override
    public void saveUserConfig(Config config) throws IOException {
        JsonUtil.saveJsonFile(config, filePath);
    }

}
