package seedu.geekeep.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.commons.exceptions.DataConversionException;
import seedu.geekeep.commons.util.FileUtil;
import seedu.geekeep.model.ReadOnlyTaskManager;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlTaskManagerStorage implements TaskManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskManagerStorage.class);

    private String filePath;

    public XmlTaskManagerStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskManager> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}
     *
     * @param filePath
     *            location of the data. Cannot be null
     * @throws DataConversionException
     *             if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyTaskManager> readAddressBook(String filePath)
            throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file " + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskManager addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskManager addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyTaskManager)}
     *
     * @param filePath
     * location of the data. Cannot be null
     */
    @Override
    public void saveAddressBook(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(addressBook));
    }

}
