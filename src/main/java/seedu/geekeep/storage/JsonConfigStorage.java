package seedu.geekeep.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.geekeep.commons.exceptions.DataConversionException;
import seedu.geekeep.commons.util.JsonUtil;
import seedu.geekeep.model.Config;

/**
 * A class to access Config stored in the hard disk as a json file
 */
public class JsonConfigStorage implements ConfigStorage {

    private String filePath;

    public JsonConfigStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<Config> readConfig() throws DataConversionException {
        return readConfig(filePath);
    }

    /**
     * Similar to {@link #readConfig()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<Config> readConfig(String prefsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(prefsFilePath, Config.class);
    }

    @Override
    public void saveConfig(Config config) throws IOException {
        JsonUtil.saveJsonFile(config, filePath);
    }

}
