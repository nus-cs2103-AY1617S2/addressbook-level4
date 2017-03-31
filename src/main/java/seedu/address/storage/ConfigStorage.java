//@@Liu Yulin A0148052L

package seedu.address.storage;

import java.io.IOException;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;

public interface ConfigStorage {
    Config readConfig() throws DataConversionException, IOException;
    void saveConfig(Config config) throws IOException;
}
