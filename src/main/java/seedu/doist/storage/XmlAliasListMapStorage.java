package seedu.doist.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.exceptions.DataConversionException;
import seedu.doist.commons.util.FileUtil;
import seedu.doist.model.ReadOnlyAliasListMap;

//@@author A0140887W-reused
/**
 * A class to access AliasListMap data stored as an xml file on the hard disk.
 */
public class XmlAliasListMapStorage implements AliasListMapStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTodoListStorage.class);

    private String filePath;

    public XmlAliasListMapStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getAliasListMapFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAliasListMap> readAliasListMap() throws DataConversionException, IOException {
        return readAliasListMap(filePath);
    }

    /**
     * Similar to {@link #readAliasListMap()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAliasListMap> readAliasListMap(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File aliasListMapFile = new File(filePath);

        if (!aliasListMapFile.exists()) {
            logger.info("AliasList Map file "  + aliasListMapFile + " not found");
            return Optional.empty();
        }

        ReadOnlyAliasListMap aliasListMapOptional = XmlFileStorage.loadAliasDataFromSaveFile(new File(filePath));

        return Optional.of(aliasListMapOptional);
    }

    @Override
    public void saveAliasListMap(ReadOnlyAliasListMap aliasList) throws IOException {
        saveAliasListMap(aliasList, filePath);
    }

    /**
     * Similar to {@link #saveAliasListMap(AliasListMap)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAliasListMap(ReadOnlyAliasListMap aliasListMap, String filePath) throws IOException {
        assert aliasListMap != null;
        assert filePath != null;
        logger.fine("XMLAliasListMapStorage: Trying to save alias list map...");

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAliasListMap(aliasListMap));
    }

    @Override
    public void setAliasListMapFilePath(String path) {
        this.filePath = path;
    }

}
