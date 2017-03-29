package seedu.geekeep.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.commons.exceptions.DataConversionException;
import seedu.geekeep.commons.util.FileUtil;
import seedu.geekeep.model.ReadOnlyGeeKeep;

/**
 * A class to access GeeKeep data stored as an xml file on the hard disk.
 */
public class XmlGeeKeepStorage implements GeeKeepStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlGeeKeepStorage.class);

    private String filePath;

    public XmlGeeKeepStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getGeeKeepFilePath() {
        return filePath;
    }

    @Override
    public void setGeeKeepFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<ReadOnlyGeeKeep> readGeeKeep() throws DataConversionException, IOException {
        return readGeeKeep(filePath);
    }

    /**
     * Similar to {@link #readGeeKeep()}
     *
     * @param filePath
     *            location of the data. Cannot be null
     * @throws DataConversionException
     *             if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyGeeKeep> readGeeKeep(String filePath)
            throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File geeKeepFile = new File(filePath);

        if (!geeKeepFile.exists()) {
            logger.info("GeeKeep file " + geeKeepFile + " not found");
            return Optional.empty();
        }

        ReadOnlyGeeKeep geeKeepOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(geeKeepOptional);
    }

    @Override
    public void saveGeeKeep(ReadOnlyGeeKeep geeKeep) throws IOException {
        saveGeeKeep(geeKeep, filePath);
    }

    /**
     * Similar to {@link #saveGeeKeep(ReadOnlyGeeKeep)}
     *
     * @param filePath
     * location of the data. Cannot be null
     */
    @Override
    public void saveGeeKeep(ReadOnlyGeeKeep geeKeep, String filePath) throws IOException {
        assert geeKeep != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableGeeKeep(geeKeep));
    }

}
