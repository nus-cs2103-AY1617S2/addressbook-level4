package seedu.taskcrusher.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.taskcrusher.commons.core.LogsCenter;
import seedu.taskcrusher.commons.exceptions.DataConversionException;
import seedu.taskcrusher.commons.util.FileUtil;
import seedu.taskcrusher.model.ReadOnlyUserInbox;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String filePath;

    public XmlAddressBookStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getUserInboxFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyUserInbox> readUserInbox() throws DataConversionException, IOException {
        return readUserInbox(filePath);
    }

    /**
     * Similar to {@link #readUserInbox()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyUserInbox> readUserInbox(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyUserInbox addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveUserInbox(ReadOnlyUserInbox addressBook) throws IOException {
        saveUserInbox(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveUserInbox(ReadOnlyUserInbox)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveUserInbox(ReadOnlyUserInbox addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

}
