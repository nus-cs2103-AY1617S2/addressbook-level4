package seedu.ezdo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.ezdo.commons.core.LogsCenter;
import seedu.ezdo.commons.exceptions.DataConversionException;
import seedu.ezdo.commons.util.FileUtil;
import seedu.ezdo.model.ReadOnlyEzDo;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlEzDoStorage implements EzDoStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEzDoStorage.class);

    private String filePath;

    public XmlEzDoStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getEzDoFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEzDo> readEzDo() throws DataConversionException, IOException {
        return readEzDo(filePath);
    }

    /**
     * Similar to {@link #readEzDo()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEzDo> readEzDo(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyEzDo addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveEzDo(ReadOnlyEzDo addressBook) throws IOException {
        saveEzDo(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveEzDo(ReadOnlyEzDo)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveEzDo(ReadOnlyEzDo addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

}
