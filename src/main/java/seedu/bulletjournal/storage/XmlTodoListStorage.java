package seedu.bulletjournal.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.bulletjournal.commons.core.LogsCenter;
import seedu.bulletjournal.commons.exceptions.DataConversionException;
import seedu.bulletjournal.commons.util.FileUtil;
import seedu.bulletjournal.model.ReadOnlyTodoList;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlTodoListStorage implements TodoListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTodoListStorage.class);

    private String filePath;

    public XmlTodoListStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getBulletJournalFilePath() {
        return filePath;
    }

    public void setBulletJournalFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<ReadOnlyTodoList> readAddressBook() throws DataConversionException, IOException {
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
    public Optional<ReadOnlyTodoList> readAddressBook(String filePath)
            throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file " + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTodoList addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList addressBook) throws IOException {
        saveTodoList(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyTodoList)}
     *
     * @param filePath
     *            location of the data. Cannot be null
     */
    @Override
    public void saveTodoList(ReadOnlyTodoList addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTodoList(addressBook));
    }

}
