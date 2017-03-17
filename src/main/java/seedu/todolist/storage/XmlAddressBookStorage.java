package seedu.todolist.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.commons.util.FileUtil;
import seedu.todolist.model.ReadOnlyToDoList;

/**
 * A class to access ToDoList data stored as an xml file on the hard disk.
 */
public class XmlAddressBookStorage implements ToDoListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String filePath;

    public XmlAddressBookStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getToDoListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException {
        return readToDoList(filePath);
    }

    /**
     * Similar to {@link #readToDoList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyToDoList> readToDoList(String filePath) throws DataConversionException,
    FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyToDoList addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveToDoList(ReadOnlyToDoList addressBook) throws IOException {
        saveToDoList(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveToDoList(ReadOnlyToDoList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveToDoList(ReadOnlyToDoList addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

}
